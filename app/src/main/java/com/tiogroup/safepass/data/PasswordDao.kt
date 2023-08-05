package com.tiogroup.safepass.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface PasswordDao {

    @Query("SELECT * FROM passwordTable")
    fun getPassword(): LiveData<List<Password>>

    @Query("SELECT * FROM passwordTable WHERE id = :id")
    fun getPasswordById(id: Int): LiveData<Password>

    @Upsert
    fun upsertPassword(password: Password):Long

    @Delete
    fun deletePassword(password: Password)
}