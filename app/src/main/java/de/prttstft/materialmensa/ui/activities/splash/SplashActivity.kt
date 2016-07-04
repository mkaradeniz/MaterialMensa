package de.prttstft.materialmensa.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.prttstft.materialmensa.ui.activities.launch.LaunchActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startLaunchActivityIntent = Intent(this, LaunchActivity::class.java)
        startActivity(startLaunchActivityIntent)

        finish()
    }
}