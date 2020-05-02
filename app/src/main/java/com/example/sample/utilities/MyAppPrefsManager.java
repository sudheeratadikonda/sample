package com.example.sample.utilities;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * MyAppPrefsManager handles some Prefs of AndroidShopApp Application
 **/


public class MyAppPrefsManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    private static final String PREF_NAME = "DIGITAL_ATTENDANCE";


    private static final String IS_USER_LOGGED_IN_ADMIN = "isLogged_in_Admin";


    public MyAppPrefsManager(Context context) {
        int PRIVATE_MODE = 0;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        prefsEditor = sharedPreferences.edit();
        prefsEditor.apply();
    }


    public void setAdminLoggedIn(boolean isAdminLoggedIn) {
        prefsEditor.putBoolean(IS_USER_LOGGED_IN_ADMIN, isAdminLoggedIn);
        prefsEditor.commit();
    }

    public boolean isAdminLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGGED_IN_ADMIN, false);
    }


}
