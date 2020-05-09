package com.frezzcoding.bolosassuncao.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.frezzcoding.bolosassuncao.models.Order

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createOrder()

    @Delete
    fun deleteOrder(order : Order)

    fun getCustomerOrders(id : Int)

    fun getAllOrders()


}