package com.frezzcoding.bolosassuncao.models


data class Order(val id : Int, val user_id : Int, var cost : Double,var customer_name : String, var delivery_time : String, var delivery_date : String,
            var mobile : String, var address_1 : String, var address_2 : String, var postcode : String, var delivery_collection : String, var instructions : String,
            var payment_type : String, var status : String)


