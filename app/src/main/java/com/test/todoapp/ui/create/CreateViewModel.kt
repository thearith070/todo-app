package com.test.todoapp.ui.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.todoapp.repo.CreateToDoRepo
import com.test.todoapp.repo.GetToDoRepo
import com.test.todoapp.data.ToDo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val repo: CreateToDoRepo,
    private val repo2: GetToDoRepo
) : ViewModel() {

    private val _create: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val create: SharedFlow<Boolean> = _create

    val update: MutableSharedFlow<Boolean> = MutableSharedFlow()
    var cacheToDo: ToDo? = null

    fun createToDo(toDo: ToDo, allowToDuplicate: Boolean = false) {
        viewModelScope.launch {
            try {
                if (!allowToDuplicate) {
                    repo.create(toDo)
                } else {
                    repo.createWithDuplicate(toDo)
                }
                _create.emit(true)
            } catch (e: Exception) {
                e.printStackTrace()
                _create.emit(false)
            }
        }
    }

    fun update(toDo: ToDo) {
        cacheToDo = toDo
        viewModelScope.launch {
            val updated = repo2.update(toDo)
            update.emit(updated)
        }
    }
}