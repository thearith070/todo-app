package com.test.todoapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tbl_todo")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val task: String,

    val description: String? = null,

    var isUpdated: Boolean = false,

    var isCompleted: Boolean = false
): Parcelable