package com.github.simonpham.sunshine.ui.prefs;

import android.os.Bundle;

import com.github.simonpham.sunshine.R;

import androidx.preference.PreferenceFragmentCompat;

/**
 * Created by Simon Pham on 3/24/19.
 * Email: simonpham.dn@gmail.com
 */

public class PreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
