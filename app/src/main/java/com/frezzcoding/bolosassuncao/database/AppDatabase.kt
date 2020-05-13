package com.frezzcoding.bolosassuncao.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.models.User


@Database(entities = [User::class, Product::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    abstract fun basketDao() : BasketDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "user_database"
                    ).build()
                }
                return instance
            }
        }
    }
}