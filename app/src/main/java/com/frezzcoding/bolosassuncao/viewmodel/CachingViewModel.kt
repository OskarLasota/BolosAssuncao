package com.frezzcoding.bolosassuncao.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frezzcoding.bolosassuncao.database.AppDatabase
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.repo.CachingRepository
import com.frezzcoding.bolosassuncao.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CachingViewModel(var _application: Application) : AndroidViewModel(_application) {

    private var repository : CachingRepository? = null
    private var _user = MutableLiveData<User>()
    var user : LiveData<User> = _user

    private var _loading = MutableLiveData<Boolean>()
    var loading : LiveData<Boolean> = _loading


    fun init(){
        repository = CachingRepository(_application)
        user = repository!!.getCurrentUser()
    }



    fun insert(tempUser: User) = viewModelScope.launch(Dispatchers.IO) {
        _loading.postValue(true)
        repository?.insert(tempUser)
        _loading.postValue(false)
    }

}