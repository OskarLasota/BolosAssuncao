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
    private var user : LiveData<User> = _user


    fun init(){
        repository = CachingRepository(_application)
        user = repository!!.getCurrentUser()
    }


    fun getUser() : LiveData<User>{
        return user
    }

    fun insert(tempUser: User) = viewModelScope.launch(Dispatchers.IO) {
        repository?.insert(tempUser)
    }

}