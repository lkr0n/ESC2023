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
                Log.e("TEST", nameText.text.toString())
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
}