package de.louiskronberg.esc

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking


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
                // check if user exists!
                runBlocking {
                    createUser(
                        nameText.text.toString(),
                        GoogleSignIn.getLastSignedInAccount(applicationContext)!!.idToken!!
                    )
                }
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


    data class UserBody(val name: String)

    private suspend fun createUser(name: String, idToken: String) {
        val url = getString(R.string.api_url) + "/user"
        val body = Gson().toJson(UserBody(name))
        val client = HttpClient(CIO)
        val result = client.post(url) {
            contentType(ContentType.Application.Json)
            headers {
                append("Id-Token", idToken)
            }
            setBody(body)
        }
        if (result.status != HttpStatusCode.OK) {
            Log.i("ERROR", result.toString())
            return
        }
    }
}