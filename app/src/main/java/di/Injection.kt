package di

import androidx.lifecycle.ViewModelProvider
import models.ProductDataSource
import repo.ProductRepository
import viewmodel.ViewModelFactory

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