package com.test.todoapp.repo

import com.test.todoapp.data.ToDo

interface GetToDoRepo {

    fun getToDo(): List<ToDo>

    fun searchToDo(query: String): List<ToDo>

    suspend fun update(toDo: ToDo): Boolean

    suspend fun updateFromCheck(toDo: ToDo)

}