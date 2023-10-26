package com.test.todoapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.todoapp.data.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToDo(doTo: ToDo)

    @Query("SELECT * FROM tbl_todo ORDER BY task ASC")
    fun getToDo(): List<ToDo>

    @Query("SELECT * FROM tbl_todo WHERE task LIKE :query")
    fun searchToDo(query: String): List<ToDo>

    @Query("SELECT * FROM tbl_todo WHERE task = :task")
    fun getByTask(task: String): ToDo?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(doTo: ToDo)

    @Query("DELETE FROM tbl_todo WHERE id =:id")
    suspend fun delete(id: Int)

}