package com.frezzcoding.bolosassuncao.models


data class Order(val id : Int, val user_id : Int, var cost : Double,var customer_name : String, var delivery_time : String, var delivery_date : String,
            var mobile : String, var address_1 : String, var address_2 : String, var postcode : String, var delivery_collection : String, var instructions : String,
            var payment_type : String, var status : String)
{
    constructor(id: Int, user_id : Int, cost : Double, customer_name: String, delivery_time: String, delivery_date: String, mobile: String, delivery_collection: String,
         payment_type: String, status: String): this(id, user_id,cost, customer_name,
                    delivery_time, delivery_date, mobile, "", "", "",delivery_collection,"",payment_type, status)
}




