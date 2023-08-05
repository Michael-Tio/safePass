package com.tiogroup.safepass.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "passwordTable")
@Parcelize
data class Password(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "EmailOrUsernameOrPhone")
    val emailOrUsernameOrPhone: String,

    @ColumnInfo(name = "password")
    val password: String,
): Parcelable