package de.louiskronberg.esc

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import kotlinx.coroutines.runBlocking


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val intent = Intent(this, UserActivity::class.java)
        val userLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                finish()
            }

        val googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val idToken =
                GoogleSignIn.getLastSignedInAccount(applicationContext)!!.idToken!!

            val user = runBlocking { UserApi.getUser(getString(R.string.api_url), idToken) }
            if (user != null) {
                finish()
            } else {
                userLauncher.launch(intent)
            }
        }

        val signInButton: SignInButton = findViewById(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)

        signInButton.setOnClickListener {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id)).build()
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleLauncher.launch(googleSignInClient.signInIntent)
        }
    }

}