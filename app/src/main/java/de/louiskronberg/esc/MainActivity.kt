package de.louiskronberg.esc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create a view object corresponding to the xml of the same name in 
        // the layout directory. This also sets this view to be the content 
        // of this activity
        setContentView(R.layout.activity_main)


        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "ESC 2023"
        setSupportActionBar(toolbar)


        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                loadRanking()
            }

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id)).build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val task = googleSignInClient.silentSignIn()

        if (task.isSuccessful) {
            loadRanking()
        } else {
            task.addOnCompleteListener {
                try {
                    task.getResult(ApiException::class.java)
                    loadRanking()
                } catch (apiException: ApiException) {
                    val intent = Intent(this, LoginActivity::class.java)
                    launcher.launch(intent)
                }
            }
        }
    }

    private fun loadRanking() {
        val ranking = runBlocking {
            Api
                .getRanking(
                    findViewById(R.id.recycler_view),
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
        val lockHandler = Handler(Looper.getMainLooper())
        val r = object : Runnable {
            override fun run() {
                val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
                CoroutineScope(Dispatchers.IO).launch {
                    checkLock(account!!.idToken!!)
                }

                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.client_id)).build()
                val googleSignInClient = GoogleSignIn.getClient(applicationContext, gso)
                runBlocking {
                    googleSignInClient.silentSignIn()
                }
                lockHandler.postDelayed(this, 10000)
            }
        }
        lockHandler.postDelayed(r, 0)

        // score check loop
        val scoreHandler = Handler(Looper.getMainLooper())
        val scoreRunnable = object : Runnable {
            override fun run() {
                val account = GoogleSignIn.getLastSignedInAccount(applicationContext)

                val adapter: CountryAdapter = recyclerView.adapter as CountryAdapter
                if (adapter.getLock()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        checkScore(account!!.idToken!!)
                    }
                }

                scoreHandler.postDelayed(this, 10000)
            }
        }
        scoreHandler.postDelayed(scoreRunnable, 0)

        // attach ItemMoveCallback to the RecyclerView making the elements of RecyclerView
        // draggable
        val callback = ItemMoveCallback(countryAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = countryAdapter

    }

    private suspend fun checkLock(idToken: String) {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val lock = Api.getLock(recyclerView, getString(R.string.api_url), idToken)
        val adapter: CountryAdapter = recyclerView.adapter as CountryAdapter

        if (!adapter.getLock() && lock) {
            runOnUiThread {
                adapter.lock()
            }
        }

        if (adapter.getLock() && !lock) {
            runOnUiThread {
                adapter.unlock()
            }
        }
    }

    private suspend fun checkScore(idToken: String) {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val score = Api.getScore(recyclerView, getString(R.string.api_url), idToken)
        val adapter: CountryAdapter = recyclerView.adapter as CountryAdapter

        if (score != null && adapter.getScore() == null) {
            runOnUiThread {
                toolbar.title = "${toolbar.title} - Score: ${score.score}"
                adapter.setScore(score)
            }
        }

        if (score == null && adapter.getScore() != null) {
            runOnUiThread {
                toolbar.title = "ESC2023"
                adapter.removeScore()
            }
        }
    }
}

