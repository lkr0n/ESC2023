package de.louiskronberg.esc

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val signInButton: SignInButton = findViewById(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            signInButton.visibility = View.INVISIBLE

            val submitButton: Button = findViewById(R.id.submit_button)
            val nameText: TextView = findViewById(R.id.name_text)
            submitButton.visibility = View.VISIBLE
            nameText.visibility = View.VISIBLE

            submitButton.setOnClickListener {
                createUser(
                    nameText.text.toString(),
                    GoogleSignIn.getLastSignedInAccount(this)!!.idToken!!
                )
                finish()
            }
        }

        signInButton.setOnClickListener {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id)).build()
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            launcher.launch(googleSignInClient.signInIntent)
        }
    }

    private fun createUser(name: String, idToken: String) {
        val volleyQueue = Volley.newRequestQueue(this)
        val url = getString(R.string.api_url) + "/user"
        val json = JSONObject()
        json.put("name", name)

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            { response ->
                Log.i("TEST", response.toString())
            },
            { error ->
                Log.i("TEST", error.toString())
            },
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Id-Token"] = idToken
                return headers
            }
        }

        volleyQueue.add(jsonObjectRequest)
    }
}