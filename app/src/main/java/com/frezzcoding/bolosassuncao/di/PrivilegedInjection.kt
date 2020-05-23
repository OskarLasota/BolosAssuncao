package com.frezzcoding.bolosassuncao.di

import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.models.PrivilegeDataSource
import com.frezzcoding.bolosassuncao.repo.PrivilegeRepository
import com.frezzcoding.bolosassuncao.viewmodel.PrivilegedViewModelFactory

object PrivilegedInjection {

    private val privilegeDataSource : PrivilegeDataSource = PrivilegeRepository()
    private val privilegeViewModelFactory = PrivilegedViewModelFactory(privilegeDataSource)

    fun providerRepository() : PrivilegeDataSource{
        return privilegeDataSource
    }

    fun provideViewModelFactory() : ViewModelProvider.Factory{
        return privilegeViewModelFactory
    }

}