package data

import models.Product
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiClient {

    private val API_BASE_URL = "https://bolosassuncao.com.br/api/"
    private var servicesApiInterface:ServicesApiInterface?=null


    fun build():ServicesApiInterface?{
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


     //   var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
     //   httpClient.addInterceptor(interceptor())

     //   var retrofit: Retrofit = builder.client(httpClient.build()).build()

        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java)

        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ServicesApiInterface{

        @GET("products.php")
        fun products(): Call<List<Product>>


    }

}