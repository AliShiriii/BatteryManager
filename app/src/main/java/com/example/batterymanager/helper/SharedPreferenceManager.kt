package com.example.batterymanager.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager {

    companion object {

        private var sharedPreferences: SharedPreferences? = null
        private var sharedPreferencesEditor: SharedPreferences.Editor? = null
        private var sharedPreferencesBoolean = "SHARED_PREFERENCES_BOOLEAN"
        private var isServiceOn = "isServiceOn"

        fun isServiceOn(context: Context): Boolean? {

            sharedPreferences =
                context.getSharedPreferences(sharedPreferencesBoolean, Context.MODE_PRIVATE)

            return sharedPreferences?.getBoolean(isServiceOn, false)

        }

        fun setServiceState(context: Context, isOn: Boolean) {

            sharedPreferences =
                context.getSharedPreferences(sharedPreferencesBoolean, Context.MODE_PRIVATE)
            sharedPreferencesEditor = sharedPreferences?.edit()
            sharedPreferencesEditor?.putBoolean(isServiceOn, isOn)
            sharedPreferencesEditor?.apply()

        }
    }
}