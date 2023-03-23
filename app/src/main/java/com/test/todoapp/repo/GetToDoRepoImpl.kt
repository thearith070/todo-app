package com.test.todoapp.repo

import com.test.todoapp.room.ToDo
import com.test.todoapp.room.ToDoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetToDoRepoImpl @Inject constructor(
    private val db: ToDoDao
) : GetToDoRepo {

    override fun getToDo(): Flow<List<ToDo>> {
        return db.getToDo()
    }

    override suspend fun update(toDo: ToDo) {
        db.update(toDo)
    }
}