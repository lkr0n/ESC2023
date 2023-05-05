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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create a view object corresponding to the xml of the same name in 
        // the layout directory. This also sets this view to be the content 
        // of this activity
        setContentView(R.layout.activity_main)

        // always sign out to avoid having to do additional logic when login expires
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id)).build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            val intent = Intent(this, LoginActivity::class.java)

            val launcher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    runBlocking { loadRanking(GoogleSignIn.getLastSignedInAccount(applicationContext)!!.idToken!!) }
                }
            launcher.launch(intent)
        } else {
            runBlocking { loadRanking(account.idToken!!) }
        }
    }

    private suspend fun loadRanking(idToken: String) {
        val url = getString(R.string.api_url) + "/ranking"
        val client = HttpClient(CIO)
        val result = client.get(url) {
            headers {
                append("Id-Token", idToken)
            }
        }

        if (result.status != HttpStatusCode.OK) {
            Log.i("ERROR", result.toString())
            setRanking(emptyList())
            return
        }

        val ranking = Gson().fromJson(result.bodyAsText(), RankingResponse::class.java)
        Log.i("RESPONSE", ranking.toString())
        val countries = Countries.countries.sortedBy { ranking.countries.indexOf(it.name) }
        setRanking(countries)
    }

    data class RankingResponse(val countries: List<String>)

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

        // attach ItemMoveCallback to the RecyclerView making the elements of RecyclerView
        // draggable
        val callback = ItemMoveCallback(countryAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = countryAdapter

        // lock check loop
        val handler = Handler(Looper.getMainLooper())
        val r = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 10000)

                val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
                runBlocking {
                    checkLock(account!!.idToken!!)
                }
            }
        }

        handler.postDelayed(r, 10000)
    }

    data class LockResponse(val lock: Boolean)

    private suspend fun checkLock(idToken: String) {
        val url = getString(R.string.api_url) + "/lock"

        val client = HttpClient(CIO)
        val result = client.get(url) {
            headers {
                append("Id-Token", idToken)
            }
        }

        if (result.status != HttpStatusCode.OK) {
            Log.i("ERROR", result.toString())
            return
        }

        val lockResponse = Gson().fromJson(result.bodyAsText(), LockResponse::class.java)
        Log.i("RESPONSE", lockResponse.toString())

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val adapter: CountryAdapter = recyclerView.adapter as CountryAdapter
        adapter.setLock(lockResponse.lock)
    }
}

