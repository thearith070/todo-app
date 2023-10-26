package com.test.todoapp.repo

import com.test.todoapp.data.ToDo

interface CreateToDoRepo {

    suspend fun create(todo: ToDo): Boolean
    suspend fun createWithDuplicate(todo: ToDo): Boolean
    suspend fun delete(id: Int)

}