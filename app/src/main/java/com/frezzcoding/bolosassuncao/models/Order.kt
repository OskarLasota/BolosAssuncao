package com.frezzcoding.bolosassuncao.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = User::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("userid"),
    onDelete = ForeignKey.CASCADE)]
)
class Order {
    @PrimaryKey
    var orderid : Int = 0
    var userid : Int = 0
    lateinit var customer_name : String
    var homedelivery : Boolean = false
    lateinit var order_date : String
    lateinit var delivery_date : String
    var cost : Double = 0.0
}