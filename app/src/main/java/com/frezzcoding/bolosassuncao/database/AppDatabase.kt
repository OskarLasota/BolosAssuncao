package com.frezzcoding.bolosassuncao.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frezzcoding.bolosassuncao.models.User


@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao

    companion object{

        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context:Context) = instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "user.db")
                .build()
    }

}