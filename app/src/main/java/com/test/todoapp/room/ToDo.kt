package com.test.todoapp.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tbl_todo")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val task: String,

    val description: String? = null,

    var isCompleted: Boolean = false
): Parcelable