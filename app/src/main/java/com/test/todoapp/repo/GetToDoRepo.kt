package com.test.todoapp.repo

import com.test.todoapp.room.ToDo
import kotlinx.coroutines.flow.Flow

interface GetToDoRepo {

    fun getToDo(): Flow<List<ToDo>>
    suspend fun update(toDo: ToDo)

}