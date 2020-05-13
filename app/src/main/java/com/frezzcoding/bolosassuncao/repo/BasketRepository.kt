package com.frezzcoding.bolosassuncao.repo

import android.app.Application
import com.frezzcoding.bolosassuncao.database.AppDatabase
import com.frezzcoding.bolosassuncao.database.BasketDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class BasketRepository(application : Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    var basketDao : BasketDao

    init {
        basketDao = AppDatabase.getInstance(application).basketDao()
    }


}