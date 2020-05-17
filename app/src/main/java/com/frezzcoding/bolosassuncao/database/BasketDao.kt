package com.frezzcoding.bolosassuncao.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.frezzcoding.bolosassuncao.models.Product

@Dao
interface BasketDao {

    @Insert
    fun addProduct(product : Product)

    @Delete
    fun deleteProduct(product : Product)

    @Query("DELETE FROM basket_table")
    fun deleteBasket()

    @Query("SELECT * FROM basket_table")
    fun getAllProducts() : LiveData<List<Product>>


}