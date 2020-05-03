package com.frezzcoding.bolosassuncao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.models.ProductDataSource
import com.frezzcoding.bolosassuncao.models.UserDataSource

class AccountViewModelFactory (private val repository : UserDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AccountViewModel(repository) as T
    }

}