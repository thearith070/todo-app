package com.test.todoapp.di

import com.test.todoapp.repo.CreateToDoRepo
import com.test.todoapp.repo.CreateToDoRepoImpl
import com.test.todoapp.repo.GetToDoRepo
import com.test.todoapp.repo.GetToDoRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRepo {

    @Binds
    abstract fun bindCreateToDoRepo(repository: CreateToDoRepoImpl): CreateToDoRepo

    @Binds
    abstract fun bindGetToDoRepo(repository: GetToDoRepoImpl): GetToDoRepo

}