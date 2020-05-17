package com.frezzcoding.bolosassuncao.repo

import android.app.Application
import com.frezzcoding.bolosassuncao.database.AppDatabase
import com.frezzcoding.bolosassuncao.database.UserDao
import com.frezzcoding.bolosassuncao.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CachingRepository(_application : Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    var userDao : UserDao

    init {
        userDao = AppDatabase.getInstance(_application).userDao()
    }

    fun getCurrentUser() = userDao.getUser()


    suspend fun insert(_user : User){
        userDao.insert(_user)
    }



}