package com.test.todoapp.repo

import com.test.todoapp.data.ToDo
import com.test.todoapp.room.ToDoDao
import javax.inject.Inject

class CreateToDoRepoImpl @Inject constructor(
    private val db: ToDoDao
) : CreateToDoRepo {

    override suspend fun create(todo: ToDo): Boolean {
        val exist = db.getByTask(todo.task)
        if (exist == null) {
            db.addToDo(todo)
            return true
        }
        return false
    }

    override suspend fun createWithDuplicate(todo: ToDo): Boolean {
        db.addToDo(todo)
        return true
    }

    override suspend fun delete(id: Int) {
        db.delete(id)
    }
}