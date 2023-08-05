package com.tiogroup.safepass.preferences

import android.content.Context

internal class UserPreferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val LOCK_AFTER_CLOSE = "lock_after_close"
        private const val PASSWORD = "password"
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setLock(lock: LockPref) {
        val editor = preferences.edit()
        editor.putBoolean(LOCK_AFTER_CLOSE, lock.isLocked)
        editor.putString(PASSWORD, lock.password)
        editor.apply()
    }
    fun getLockStatus(): Boolean{
        return preferences.getBoolean(LOCK_AFTER_CLOSE, false)
    }

    fun getPassword(): String {
        return preferences.getString(PASSWORD, "").toString()
    }
}