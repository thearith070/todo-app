package com.test.todoapp.repo

import com.test.todoapp.room.ToDo

interface CreateToDoRepo {

    suspend fun create(todo: ToDo)
    suspend fun delete(id: Int)

}