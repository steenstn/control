package com.echoandroid;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class PreferencesActivity
    extends PreferenceActivity
    implements OnSharedPreferenceChangeListener {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        setUpListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressWarnings("deprecation")
    private void setUpListeners() {

        EditTextPreference ip = (EditTextPreference) findPreference("ip");
        ip.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                EditTextPreference edit = new EditTextPreference(getApplicationContext());

                return true;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preference, String key) {
        // TODO Auto-generated method stub

    }

}
