package com.test.todoapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.todoapp.repo.CreateToDoRepo
import com.test.todoapp.repo.GetToDoRepo
import com.test.todoapp.room.ToDo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repo: GetToDoRepo,
    private val repo2: CreateToDoRepo
) : ViewModel() {

    fun getList(): Flow<List<ToDo>> = repo.getToDo()

    fun update(toDo: ToDo) {
        viewModelScope.launch {
            repo.update(toDo)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            repo2.delete(id)
        }
    }

    fun create(toDo: ToDo) {
        viewModelScope.launch {
            repo2.create(toDo)
        }
    }
}