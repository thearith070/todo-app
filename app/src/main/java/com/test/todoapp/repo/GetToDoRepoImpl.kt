package com.test.todoapp.repo

import com.test.todoapp.data.ToDo
import com.test.todoapp.room.ToDoDao
import javax.inject.Inject

class GetToDoRepoImpl @Inject constructor(
    private val db: ToDoDao
) : GetToDoRepo {

    override fun getToDo(): List<ToDo> {
        return db.getToDo()
    }

    override fun searchToDo(query: String): List<ToDo> {
        return if (query.isEmpty()) db.getToDo()
        else db.searchToDo("%$query%")
    }

    override suspend fun updateFromCheck(toDo: ToDo) {
        db.update(toDo)
    }

    override suspend fun update(toDo: ToDo): Boolean {
        val exist = db.getByTask(toDo.task)
        if (exist == null) {
            db.update(toDo.apply {
                isUpdated = true
            })
            return true
        }
        return false
    }
}