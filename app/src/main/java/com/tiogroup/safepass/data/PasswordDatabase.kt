package com.tiogroup.safepass.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Password::class], version = 1, exportSchema = false)
abstract class PasswordDatabase: RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
    companion object {
        @Volatile
        private var INSTANCE: PasswordDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): PasswordDatabase {
            if (INSTANCE == null) {
                synchronized(PasswordDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PasswordDatabase::class.java, "password_database")
                        .build()
                }
            }
            return INSTANCE as PasswordDatabase
        }
    }
}