package com.israel.israellab8empmanager

import android.content.Context

object AuthUtils {
    private const val AUTH_PREFS_NAME = "auth_preferences"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_USERNAME = "username"

    fun setUserDetails(username: String, accessToken: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(AUTH_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_USERNAME, username)
            apply()
        }
    }

    fun isAuthenticated(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(AUTH_PREFS_NAME, Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
        return accessToken != null
    }

    fun logout(context: Context) {
        val sharedPreferences = context.getSharedPreferences(AUTH_PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    fun getAccessToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(AUTH_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }
}
