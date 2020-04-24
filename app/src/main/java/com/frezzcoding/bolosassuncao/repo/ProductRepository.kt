package com.frezzcoding.bolosassuncao.repo

import com.frezzcoding.bolosassuncao.data.ApiClient
import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadBody
import com.frezzcoding.bolosassuncao.data.UploadResult
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.models.ProductDataSource
import okhttp3.MediaType;
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ProductRepository() : ProductDataSource {

    private var call: Call<ArrayList<Product>> ?= null
    private var uploadcall : Call<UploadResult> ?= null

    override fun uploadProduct(product: Product, callback: OperationCallBack<Boolean>) {
        var body = UploadBody(product.name, product.encode, product.price, product.description)

        //println(body)
        uploadcall = ApiClient.build()?.upload(product.name, product.encode)

        //this needs completing
        // i dont know how to upload a file

        //uploadcall= ApiClient.build()?.upload()
        uploadcall?.enqueue(object : Callback<UploadResult>{
            override fun onFailure(call: Call<UploadResult>, t: Throwable) {
                println("on failure " + t.toString())
                println(t.cause)
            }

            override fun onResponse(call: Call<UploadResult>, response: Response<UploadResult>) {
                println("on success")
                //check if response.error is false - meaning no errors occured



            }

        })

    }


    override fun retrieveProducts(callback: OperationCallBack<Product>) {
        call= ApiClient.build()?.products()
        call?.enqueue(object : Callback<ArrayList<Product>>{
            override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<ArrayList<Product>>, response: Response<ArrayList<Product>>) {
                response?.body()?.let {
                    if (response.isSuccessful){
                        var list : ArrayList<Product> = response.body()!!
                        callback.onSuccess(list)
                    }else{
                        callback.onError(it.toString())
                    }
                }
            }

        })
    }



    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }



}