package com.kotlinsession.mapwithprefs.prefs

import android.content.Context
import android.content.SharedPreferences


class AppPreferences(context: Context) {

  private val sharedPreferences: SharedPreferences =
       context.getSharedPreferences(PrefKeys.PREF_NAME, Context.MODE_PRIVATE)




    companion object {
        private var INSTANCE: AppPreferences? = null
        fun getInstance(context: Context): AppPreferences? {
            var instance = INSTANCE
            if (instance == null) {
                INSTANCE = AppPreferences(context)
            }
            return INSTANCE
        }
    }

    fun saveString(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun getString(KEY_NAME: String): String? {
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun clear(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

}
