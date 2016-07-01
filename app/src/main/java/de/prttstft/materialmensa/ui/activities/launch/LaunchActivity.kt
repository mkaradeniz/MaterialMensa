package de.prttstft.materialmensa.ui.activities.launch

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import de.prttstft.materialmensa.ui.activities.main.MainActivity
import timber.log.Timber

class LaunchActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null;
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                val startMainActivityIntent = Intent(this@LaunchActivity, MainActivity::class.java)
                startActivity(startMainActivityIntent)

                finish()
            } else {
                auth!!.signInAnonymously().addOnCompleteListener(this, { task ->

                    if (!task.isSuccessful) {
                        Timber.e(task.exception?.message)
                    }
                })

            }
        }
    }

    public override fun onStart() {
        super.onStart()

        auth!!.addAuthStateListener(authStateListener!!)
    }

    public override fun onStop() {
        super.onStop()

        if (authStateListener != null) {
            auth!!.removeAuthStateListener(authStateListener!!)
        }
    }
}
