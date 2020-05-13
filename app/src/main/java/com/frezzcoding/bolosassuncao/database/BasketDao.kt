package com.frezzcoding.bolosassuncao.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.Product

@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(product : Product)

    @Delete
    fun deleteProduct(product : Product)

    @Query("DELETE FROM basket_table")
    fun deleteBasket()

    @Query("SELECT * FROM basket_table")
    fun getAllProducts() : LiveData<List<Product>>


}