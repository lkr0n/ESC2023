package de.louiskronberg.esc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson


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
                    loadRanking(GoogleSignIn.getLastSignedInAccount(this)!!.idToken!!)
                }
            launcher.launch(intent)
        } else {
            loadRanking(account.idToken!!)
        }
    }

    private fun loadRanking(idToken: String) {
        val volleyQueue = Volley.newRequestQueue(this)
        val url = getString(R.string.api_url) + "/ranking"

        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET,
            url,
            null,
            { response ->
                Log.i("RESPONSE", response.toString())
                val gson = Gson()
                val ranking = gson.fromJson(
                    response.toString(),
                    RankingResponse::class.java
                )
                val countries = Countries.countries.sortedBy { ranking.countries.indexOf(it.name) }
                setRanking(countries)
            },
            { error ->
                Log.i("ERROR", error.toString())
                setRanking(emptyList())
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Id-Token"] = idToken
                return params
            }
        }

        volleyQueue.add(jsonObjectRequest)
    }

    data class RankingResponse(val countries: List<String>)

    private fun setRanking(countries: List<Countries.Country>) {
        // find a RecyclerView defined in the xml layout files and populate it
        // with an adapter wrapping the list of countries to be displayed
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        Log.i("COUNTRIES", countries.toString())
        val countryAdapter = CountryAdapter(countries) { country ->
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
    }
}

