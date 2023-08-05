package com.tiogroup.safepass.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tiogroup.safepass.data.Password
import com.tiogroup.safepass.data.PasswordRepository

class HomeViewModel(private val passwordRepository: PasswordRepository): ViewModel() {
    val passwords: LiveData<List<Password>> = passwordRepository.getPassword()
}