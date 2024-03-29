package com.github.simonpham.sunshine.ui.prefs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.github.simonpham.sunshine.R;
import com.github.simonpham.sunshine.SingletonIntances;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import static com.github.simonpham.sunshine.Consts.DEVELOPER_ID;
import static com.github.simonpham.sunshine.Consts.GITHUB_REPO;
import static com.github.simonpham.sunshine.Consts.KEY_CURRENT_LOCATION;
import static com.github.simonpham.sunshine.Consts.KEY_FEEDBACK;
import static com.github.simonpham.sunshine.Consts.KEY_MORE_APPS;
import static com.github.simonpham.sunshine.Consts.KEY_RATE_US;
import static com.github.simonpham.sunshine.Consts.KEY_REPOSITORY;
import static com.github.simonpham.sunshine.Consts.KEY_UPDATE_ALL;
import static com.github.simonpham.sunshine.Consts.KEY_UPDATE_INTERVAL;
import static com.github.simonpham.sunshine.Consts.PACKAGE_NAME;
import static com.github.simonpham.sunshine.util.Utils.openPlayStore;
import static com.github.simonpham.sunshine.util.Utils.openUrl;
import static com.github.simonpham.sunshine.util.Utils.sendEmail;
import static com.github.simonpham.sunshine.util.Utils.setupNotificationRequest;

/**
 * Created by Simon Pham on 3/24/19.
 * Email: simonpham.dn@gmail.com
 */

public class PreferencesFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SingletonIntances.getSharedPrefs().getPrefs().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SingletonIntances.getSharedPrefs().getPrefs().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onSharedPreferenceChanged(SingletonIntances.getSharedPrefs().getPrefs(), KEY_UPDATE_ALL);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_CURRENT_LOCATION) || key.equals(KEY_UPDATE_ALL)) {
            Preference pref = findPreference(KEY_CURRENT_LOCATION);
            pref.setSummary(((EditTextPreference) pref).getText());
        }
        if (key.equals(KEY_UPDATE_INTERVAL) || key.equals(KEY_UPDATE_ALL)) {
            Preference pref = findPreference(KEY_UPDATE_INTERVAL);
            pref.setSummary(((ListPreference) pref).getEntry());

            if (key.equals(KEY_UPDATE_INTERVAL)) {
                setupNotificationRequest();
            }
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference pref) {
        switch (pref.getKey()) {
            case KEY_FEEDBACK:
                sendEmail(getContext(), getString(R.string.feedback_email_subject), "");
                return true;
            case KEY_REPOSITORY:
                openUrl(getContext(), GITHUB_REPO, null);
                return true;
            case KEY_RATE_US:
                openPlayStore(getContext(), PACKAGE_NAME, false);
                return true;
            case KEY_MORE_APPS:
                openPlayStore(getContext(), DEVELOPER_ID, true);
                return true;
            default:
                return super.onPreferenceTreeClick(pref);
        }
    }
}
