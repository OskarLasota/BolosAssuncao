package com.frezzcoding.bolosassuncao.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.frezzcoding.bolosassuncao.models.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user : User)

    @Delete
    fun delete(user : User)

    @Query("select * from user_table")
    fun getUser() : LiveData<User>


}