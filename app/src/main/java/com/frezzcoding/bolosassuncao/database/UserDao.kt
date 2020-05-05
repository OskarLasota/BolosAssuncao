package com.frezzcoding.bolosassuncao.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.frezzcoding.bolosassuncao.models.User


@Dao
interface UserDao {

    @Insert
    fun insert(user : User)

    @Delete
    fun delete(user : User)

    @Query("select * from user_table")
    fun getUser() : LiveData<User>


}