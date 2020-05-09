package com.frezzcoding.bolosassuncao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.models.OrderDataSource

class OrderViewModelFactory (private val repository : OrderDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderViewModel(repository) as T
    }

}