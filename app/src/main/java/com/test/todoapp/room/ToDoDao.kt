package com.test.todoapp.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDo(doTo: ToDo)

    @Query("SELECT * FROM tbl_todo ORDER BY isCompleted ASC")
    fun getToDo(): Flow<List<ToDo>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(doTo: ToDo)

    @Query("DELETE FROM tbl_todo WHERE id =:id")
    suspend fun delete(id: Int)

}