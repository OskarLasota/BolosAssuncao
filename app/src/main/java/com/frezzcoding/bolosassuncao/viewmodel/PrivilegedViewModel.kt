package com.frezzcoding.bolosassuncao.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frezzcoding.bolosassuncao.models.PrivilegeDataSource
import com.frezzcoding.bolosassuncao.models.Privileged
import com.frezzcoding.bolosassuncao.models.UserDataSource

class PrivilegedViewModel (private val repository : PrivilegeDataSource) : ViewModel() {

    private val _priv = MutableLiveData<Privileged>()
    val priv : LiveData<Privileged> = _priv


}