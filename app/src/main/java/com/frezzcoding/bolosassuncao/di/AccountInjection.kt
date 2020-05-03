package com.frezzcoding.bolosassuncao.di

import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.models.ProductDataSource
import com.frezzcoding.bolosassuncao.models.UserDataSource
import com.frezzcoding.bolosassuncao.repo.ProductRepository
import com.frezzcoding.bolosassuncao.repo.UserRepository
import com.frezzcoding.bolosassuncao.viewmodel.AccountViewModelFactory
import com.frezzcoding.bolosassuncao.viewmodel.ViewModelFactory

object AccountInjection {

    private val userDataSource : UserDataSource = UserRepository()
    private val userViewModelFactory = AccountViewModelFactory(userDataSource)

    fun providerRepository() : UserDataSource {
        return userDataSource
    }

    fun provideViewModelFactory() : ViewModelProvider.Factory{
        return userViewModelFactory
    }


}