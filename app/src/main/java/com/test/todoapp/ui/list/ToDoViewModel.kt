package com.test.todoapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.todoapp.data.ToDo
import com.test.todoapp.repo.CreateToDoRepo
import com.test.todoapp.repo.GetToDoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val getRepo: GetToDoRepo,
    private val createRepo: CreateToDoRepo
) : ViewModel() {

    private val _list: MutableLiveData<List<ToDo>> = MutableLiveData()
    val list: LiveData<List<ToDo>> = _list

    var searchQuery: String = ""

    fun search(query: String) {
        _list.value = getRepo.searchToDo(query)
    }

    fun update(toDo: ToDo) {
        viewModelScope.launch {
            getRepo.updateFromCheck(toDo)
            getToDo()
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            createRepo.delete(id)
            getToDo()
        }
    }

    fun create(toDo: ToDo) {
        viewModelScope.launch {
            createRepo.create(toDo)
            getToDo()
        }
    }

    fun getToDo() {
        _list.value = getRepo.getToDo()
    }
}