package com.wildfire.adv160421010uts.viewmodel

import android.content.Context
import android.content.SharedPreferences

class PrefManager(var context: Context?) {
    // Shared pref mode
    val PRIVATE_MODE = 0

    // Sharedpref file name
    private val PREF_NAME = "SharedPreferences"

    private val IS_LOGIN = "is_login"

    var pref: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref?.edit()

    fun setLoggin(isLogin: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }

    fun setUsername(username: String?) {
        editor?.putString("username", username)
        editor?.commit()
    }

    fun isLogin(): Boolean? {
        return pref?.getBoolean(IS_LOGIN, false)
    }

    fun getUsername(): String? {
        return pref?.getString("username", "")
    }

    fun removeData() {
        editor?.clear()
        editor?.commit()
    }
}