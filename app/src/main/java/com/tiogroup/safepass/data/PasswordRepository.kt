package com.tiogroup.safepass.data

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PasswordRepository(private val passwordDao: PasswordDao, private val executor: ExecutorService) {
    companion object {

        @Volatile
        private var instance: PasswordRepository? = null

        fun getInstance(context: Context): PasswordRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = PasswordDatabase.getDatabase(context)
                    instance = PasswordRepository(
                        database.passwordDao(),
                        Executors.newSingleThreadExecutor()
                    )
                }
                return instance as PasswordRepository
            }

        }
    }

    fun getPassword(): LiveData<List<Password>> {
        return passwordDao.getPassword()
    }

    fun getPasswordById(id: Int): LiveData<Password> {
        return passwordDao.getPasswordById(id)
    }

    fun upsertPassword(newPassword: Password):Long {
        val habit = Callable { passwordDao.upsertPassword(newPassword) }
        val submit = executor.submit(habit)
        return submit.get()
    }

    fun deletePassword(password: Password){
        executor.execute{
            passwordDao.deletePassword(password)
        }
    }
}