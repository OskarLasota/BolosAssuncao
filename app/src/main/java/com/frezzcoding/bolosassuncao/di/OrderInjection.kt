package com.frezzcoding.bolosassuncao.di

import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.models.OrderDataSource
import com.frezzcoding.bolosassuncao.repo.OrderRepository
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModelFactory

object OrderInjection {
    private val userDataSource : OrderDataSource = OrderRepository()
    private val userViewModelFactory = OrderViewModelFactory(userDataSource)

    fun providerRepository() : OrderDataSource {
        return userDataSource
    }

    fun provideViewModelFactory() : ViewModelProvider.Factory{
        return userViewModelFactory
    }


}