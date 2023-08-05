package com.tiogroup.safepass.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tiogroup.safepass.data.Password
import com.tiogroup.safepass.data.PasswordRepository

class CreateViewModel(private val passwordRepository: PasswordRepository): ViewModel() {
    fun addPassword(password: Password) {
        passwordRepository.upsertPassword(password)
    }
    fun getDetailPassword(id:Int): LiveData<Password> {
        return passwordRepository.getPasswordById(id)
    }
}