package de.louiskronberg.esc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create a view object corresponding to the xml of the same name in 
        // the layout directory. This also sets this view to be the content 
        // of this activity
        setContentView(R.layout.activity_main)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            val intent = Intent(this, LoginActivity::class.java)

            val launcher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    loadRanking()
                }
            launcher.launch(intent)
        } else {
            loadRanking()
        }
    }

    private fun loadRanking() {
        val ranking = runBlocking {
            Api
                .getRanking(
                    getString(R.string.api_url),
                    GoogleSignIn.getLastSignedInAccount(applicationContext)!!.idToken!!
                )
        }
        val countries = Countries.countries.sortedBy { ranking.indexOf(it.name) }
        setRanking(countries)
    }

    private fun setRanking(countries: List<Countries.Country>) {
        // find a RecyclerView defined in the xml layout files and populate it
        // with an adapter wrapping the list of countries to be displayed
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        Log.i("COUNTRIES", countries.toString())
        val countryAdapter = CountryAdapter(countries, applicationContext) { country ->
            val bottom = BottomSheetDialog(this)
            bottom.setContentView(R.layout.bottom_sheet_dialog)
            val textView: TextView = bottom.findViewById(R.id.bottom_text)!!
            textView.text = getString(R.string.country_description, country.artist, country.song)
            bottom.show()
        }

        // lock check loop
        val handler = Handler(Looper.getMainLooper())
        val r = object : Runnable {
            override fun run() {
                val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
                CoroutineScope(Dispatchers.IO).launch {
                    checkLock(account!!.idToken!!)
                }
                handler.postDelayed(this, 10000)
            }
        }

        handler.postDelayed(r, 0)

        // attach ItemMoveCallback to the RecyclerView making the elements of RecyclerView
        // draggable
        val callback = ItemMoveCallback(countryAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = countryAdapter

    }

    private suspend fun checkLock(idToken: String) {
        val lock = Api.getLock(getString(R.string.api_url), idToken)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val adapter: CountryAdapter = recyclerView.adapter as CountryAdapter
        adapter.setLock(lock)
    }
}

