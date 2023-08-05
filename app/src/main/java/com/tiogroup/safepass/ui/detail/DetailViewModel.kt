package com.tiogroup.safepass.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiogroup.safepass.data.Password
import com.tiogroup.safepass.data.PasswordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel (private val passwordRepository: PasswordRepository): ViewModel() {

//    private val _password = MutableLiveData<Password>()
//    val password: LiveData<Password> = _password
//
//    fun getDetailPass(id: Int){
//        _password.value = passwordRepository.getPasswordById(id)
//    }

    fun getDetailPassword(id:Int): LiveData<Password>{
        return passwordRepository.getPasswordById(id)
    }

//    fun getDetailPassword(id: Int, onResult: (password: Password?) -> Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val passwordData = passwordRepository.getPasswordById(id)
//            withContext(Dispatchers.Main) {
//                onResult(passwordData)
//            }
//        }
//    }

    fun deletePassword(password: Password) {
        viewModelScope.launch(Dispatchers.IO) {
            passwordRepository.deletePassword(password)
        }
    }
}