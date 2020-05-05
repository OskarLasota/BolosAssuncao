package com.frezzcoding.bolosassuncao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frezzcoding.bolosassuncao.models.User


@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
}