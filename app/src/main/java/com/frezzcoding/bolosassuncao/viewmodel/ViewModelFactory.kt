package com.frezzcoding.bolosassuncao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.models.ProductDataSource
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository : ProductDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductViewModel(repository) as T
    }

}
