package com.test.todoapp.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.todoapp.repo.CreateToDoRepo
import com.test.todoapp.repo.CreateToDoRepoImpl
import com.test.todoapp.repo.GetToDoRepo
import com.test.todoapp.room.ToDo
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

    fun createToDo(toDo: ToDo) {
        viewModelScope.launch {
            try {
                repo.create(toDo)
                _create.emit(true)
            } catch (e: Exception) {
                _create.emit(false)
            }

        }
    }

    fun update(toDo: ToDo) {
        viewModelScope.launch {
            repo2.update(toDo)
            update.emit(true)
        }
    }
}