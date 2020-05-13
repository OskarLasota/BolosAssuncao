package com.frezzcoding.bolosassuncao.database

import androidx.room.*
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.Product

@Dao
interface BasketDao {

    @Insert
    fun addProduct()

    @Delete
    fun deleteProduct(product : Product)

    @Query("DELETE FROM basket_table")
    fun deleteBasket()

    @Query("SELECT * FROM basket_table")
    fun getAllProducts()


}