package com.test.todoapp.repo

import com.test.todoapp.room.ToDo
import com.test.todoapp.room.ToDoDao
import javax.inject.Inject

class CreateToDoRepoImpl @Inject constructor(
    private val db: ToDoDao
) : CreateToDoRepo {

    override suspend fun create(todo: ToDo) {
        db.addToDo(todo)
    }

    override suspend fun delete(id: Int) {
        db.delete(id)
    }
}