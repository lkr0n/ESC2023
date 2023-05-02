package de.louiskronberg.esc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create a view object corresponding to the xml of the same name in 
        // the layout directory. This also sets this view to be the content 
        // of this activity
        setContentView(R.layout.activity_main)

        // find a RecyclerView defined in the xml layout files and populate it
        // with an adapter wrapping the list of countries to be displayed
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val countryAdapter = CountryAdapter(Countries.countries) { country ->
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

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id)).build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        // always logout for testing purposes
        googleSignInClient.signOut()

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            val intent = Intent(this, LoginActivity::class.java)

            val launcher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    loadRanking(GoogleSignIn.getLastSignedInAccount(this)!!.idToken!!)
                }
            launcher.launch(intent)
        } else {
            loadRanking(account.idToken!!)
        }
    }

    private fun loadRanking(idToken: String) {
        val volleyQueue = Volley.newRequestQueue(this)
        val url = getString(R.string.ranking_url, idToken)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                Log.i("TEST", response.toString())
            },
            { error ->
                Log.i("TEST", error.toString())
            }
        )

        volleyQueue.add(jsonObjectRequest)
    }
}

