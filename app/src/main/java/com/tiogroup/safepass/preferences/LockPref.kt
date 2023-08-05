package com.tiogroup.safepass.preferences

data class LockPref(
    var password: String? = null,
    var isLocked: Boolean = false
)
