package de.prttstft.materialmensa.ui.activities.launch

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import de.prttstft.materialmensa.extras.UserSettings
import de.prttstft.materialmensa.extras.UtilitiesKt
import de.prttstft.materialmensa.ui.activities.main.MainActivity
import timber.log.Timber

class LaunchActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (UtilitiesKt.arePlayServicesInstalled(this)) {
            auth = FirebaseAuth.getInstance()

            authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                val user = firebaseAuth.currentUser
                if (user != null) {
                    launchMainActivity()
                    finish()
                } else {
                    auth!!.signInAnonymously().addOnCompleteListener(this, { task ->
                        if (!task.isSuccessful) {
                            Timber.e(task.exception?.message)

                            UserSettings.disableSocialFeatures()

                            launchMainActivity()
                            finish()
                        }
                    })

                }
            }
        } else {
            UserSettings.disableSocialFeatures()

            launchMainActivity()
            finish()
        }
    }


    public override fun onStart() {
        super.onStart()

        if (auth != null) {
            auth!!.addAuthStateListener(authStateListener!!)
        }
    }

    public override fun onStop() {
        super.onStop()

        if (authStateListener != null) {
            auth!!.removeAuthStateListener(authStateListener!!)
        }
    }


    private fun launchMainActivity() {
        val startMainActivityIntent = Intent(this@LaunchActivity, MainActivity::class.java)
        startActivity(startMainActivityIntent)
    }
}
