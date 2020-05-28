package com.frezzcoding.bolosassuncao.data

import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.Privileged
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

object ApiClient {

    private const val API_BASE_URL = "http://bolosassuncao.com.br/api/"
    private var servicesApiInterface:ServicesApiInterface?=null


    fun build():ServicesApiInterface?{
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java)

        return servicesApiInterface as ServicesApiInterface
    }


    interface ServicesApiInterface{


        @FormUrlEncoded
        @POST("update_timetable.php")
        fun updateTimetable(@Field("monday") monday : String, @Field("tuesday") tuesday : String,@Field("wednesday") wednesday : String,
                            @Field("thursday") thursday : String,@Field("friday") friday : String,@Field("saturday") saturday : String,
                            @Field("sunday") sunday : String,@Field("start_time") start : String,@Field("end_time") end : String): Call<UploadResult>

        @GET("timetable.php")
        fun privileged(): Call<ArrayList<Privileged>>

        @GET("check_username_exists.php")
        fun usernames(): Call<ArrayList<User>>

        @GET("orders.php")
        fun orders(): Call<ArrayList<Order>>

        @GET("products.php")
        fun products(): Call<ArrayList<Product>>

        @GET("order_overview.php")
        fun order_overview(): Call<ArrayList<OrdersOverviewResult>>

        //using POST requests for all below requests due to issues with php

        @FormUrlEncoded
        @POST("delete_order.php")
        fun deleteOrder(@Field("id") id : Int): Call<Int>

        @FormUrlEncoded
        @POST("upload.php")
        fun upload(@Field("name") name : String, @Field("image") image : String,
                   @Field("price") price : Double, @Field("desc") desc : String): Call<UploadResult>

        @FormUrlEncoded
        @POST("update.php")
        fun update(@Field("id") id : Int, @Field("name") name : String, @Field("image") image : String,
                   @Field("price") price : Double, @Field("desc") desc : String): Call<UploadResult>

        @FormUrlEncoded
        @POST("delete.php")
        fun delete(@Field("id") id : Int): Call<UploadResult>

        @FormUrlEncoded
        @POST("login.php")
        fun login(@Field("username") username : String, @Field("password") password : String): Call<UserResult>

        @FormUrlEncoded
        @POST("register.php")
        fun register(@Field("username") username : String, @Field("password") password : String, @Field("email") email : String) : Call<UserResult>

        @FormUrlEncoded
        @POST("submit_order.php")
        fun submit_order(@Field("id") username : Int, @Field("user_id") user_id : Int,@Field("cost") cost : Double , @Field("customer_name") name : String,
                         @Field("delivery_time") delivery_time : String, @Field("delivery_date") delivery_date : String,
                         @Field("mobile") mobile : String, @Field("address_1") address_1 : String, @Field("address_2") address_2 : String,
                         @Field("postcode") postcode : String,@Field("delivery_collection") delivery_collection : String,
                         @Field("instructions") instructions : String, @Field("payment_type") payment_type : String, @Field("status") status : String) : Call<Int>

        @FormUrlEncoded
        @POST("submit_order_product.php")
        fun submit_order_product(@Field("bolo_id") bolo_id : Int, @Field("order_id") order_id : Int) : Call<Boolean>

    }

}