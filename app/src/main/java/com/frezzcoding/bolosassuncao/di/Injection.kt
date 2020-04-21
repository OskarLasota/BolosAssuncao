package com.frezzcoding.bolosassuncao.di

import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.models.ProductDataSource
import com.frezzcoding.bolosassuncao.repo.ProductRepository
import com.frezzcoding.bolosassuncao.viewmodel.ViewModelFactory

object Injection {

    private val productDataSource : ProductDataSource = ProductRepository()
    private val productViewModelFactory = ViewModelFactory(productDataSource)

    fun providerRepository() : ProductDataSource{
        return productDataSource
    }

    fun provideViewModelFactory() : ViewModelProvider.Factory{
        return productViewModelFactory
    }

}