package com.test.todoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.todoapp.data.ToDo

@Database(entities = [ToDo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao


}