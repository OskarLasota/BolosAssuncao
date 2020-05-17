package com.frezzcoding.bolosassuncao.data

import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.Product
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

        @GET("orders.php")
        fun orders(): Call<ArrayList<Order>>

        @GET("products.php")
        fun products(): Call<ArrayList<Product>>

        //using POST requests for all below requests due to issues with php
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

    }

}