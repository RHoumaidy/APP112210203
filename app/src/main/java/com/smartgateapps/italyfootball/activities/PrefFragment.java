package com.smartgateapps.italyfootball.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.smartgateapps.italyfootball.R;
import com.smartgateapps.italyfootball.model.Legue;
import com.smartgateapps.italyfootball.italy.MyApplication;

import java.util.List;

/**
 * Created by Raafat on 12/01/2016.
 */
public class PrefFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private List<Legue> allLegues;

    private SwitchPreference masriLeaguePref,
            masriCupPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        masriLeaguePref = (SwitchPreference) findPreference(getString(R.string.italy_league_notificatin_pref_key));
        masriCupPref = (SwitchPreference) findPreference(getString(R.string.italy_cup_notification_pref_key));

        boolean masriLeagueNotification = MyApplication.pref.getBoolean(getString(R.string.italy_league_notificatin_pref_key), false);
        boolean masriCupNotification = MyApplication.pref.getBoolean(getString(R.string.italy_cup_notification_pref_key), false);

        masriLeaguePref.setChecked(masriLeagueNotification);
        masriCupPref.setChecked(masriCupNotification);

    }

    @Override
    public void onResume() {
        super.onResume();

        masriLeaguePref.setOnPreferenceChangeListener(this);
        masriCupPref.setOnPreferenceChangeListener(this);

        MyApplication.pref.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        MyApplication.pref.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String key = preference.getKey();
        if (preference instanceof SwitchPreference) {
            boolean value = (boolean) newValue;

            if (key.equalsIgnoreCase(getString(R.string.italy_league_notificatin_pref_key))) {
                MyApplication
                        .pref.
                        edit()
                        .putBoolean(getString(R.string.italy_league_notificatin_pref_key), value)
                        .apply();

            }  else if (key.equalsIgnoreCase(getString(R.string.italy_cup_notification_pref_key))) {
                MyApplication
                        .pref.
                        edit()
                        .putBoolean(getString(R.string.italy_cup_notification_pref_key), value)
                        .apply();

            }
            return true;

        }
        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        boolean masriLeagueNotification = MyApplication.pref.getBoolean(getString(R.string.italy_league_notificatin_pref_key), false);
        boolean masriCupNotificatoin = MyApplication.pref.getBoolean(getString(R.string.italy_cup_notification_pref_key), false);

        if (key.equalsIgnoreCase(getString(R.string.italy_league_notificatin_pref_key))) {
            masriLeaguePref.setChecked(masriLeagueNotification);

        } else if (key.equalsIgnoreCase(getString(R.string.italy_cup_notification_pref_key))) {
            masriCupPref.setChecked(masriCupNotificatoin);

        }
    }
}


