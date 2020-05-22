package com.frezzcoding.bolosassuncao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.models.PrivilegeDataSource

@Suppress("UNCHECKED_CAST")
class PrivilegedViewModelFactory (private val repository : PrivilegeDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PrivilegedViewModel(repository) as T
    }

}