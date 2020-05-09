package com.frezzcoding.bolosassuncao.repo

import androidx.lifecycle.LiveData
import com.frezzcoding.bolosassuncao.database.UserDao
import com.frezzcoding.bolosassuncao.models.User

class CachingRepository(var userDao: UserDao) {

    var user : LiveData<User> = userDao.getUser()

     fun getCurrentUser() : LiveData<User>{
         return user
    }

    suspend fun insert(_user : User){
        userDao.insert(_user)
    }


}