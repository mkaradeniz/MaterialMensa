package de.prttstft.materialmensa.ui.activities.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import de.prttstft.materialmensa.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.user_settings);
    }
}