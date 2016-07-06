package de.prttstft.materialmensa.ui.activities.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import butterknife.ButterKnife
import de.prttstft.materialmensa.R

class SettingsActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ButterKnife.bind(this)

        setUpToolbar()
    }

    private fun setUpToolbar() {
        toolbar = findViewById(R.id.activity_settings_toolbar) as Toolbar

        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        val upIntent = supportParentActivityIntent

        if (upIntent != null) {
            supportNavigateUpTo(upIntent)
        } else {
            finish()
        }
    }
}