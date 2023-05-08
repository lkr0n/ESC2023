package de.louiskronberg.esc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.runBlocking

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val submitButton: Button = findViewById(R.id.submit_button)
        val nameText: TextView = findViewById(R.id.name_text)

        submitButton.setOnClickListener {
            val idToken =
                GoogleSignIn.getLastSignedInAccount(applicationContext)!!.idToken!!
            runBlocking {
                Api.createUser(
                    nameText,
                    getString(R.string.api_url),
                    nameText.text.toString(),
                    idToken
                )
            }
            finish()
        }
    }

}