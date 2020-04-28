package com.frezzcoding.bolosassuncao.repo

import com.frezzcoding.bolosassuncao.data.ApiClient
import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack
import com.frezzcoding.bolosassuncao.data.UploadResult
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.models.ProductDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ProductRepository() : ProductDataSource {

    private var call: Call<ArrayList<Product>> ?= null
    private var uploadcall : Call<UploadResult> ?= null
    private var updatecall : Call<UploadResult> ?= null


    override fun deleteProduct(product: Product, callback: UploadCallBack<Boolean>) {

    }

    override fun updateProduct(product: Product, callback: UploadCallBack<Boolean>) {
        //upload body didn't work when using @Body
        updatecall = ApiClient.build()?.update(product.id, product.name, product.encode, product.price, product.description)

        updatecall?.enqueue(object : Callback<UploadResult>{
            override fun onFailure(call: Call<UploadResult>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(call: Call<UploadResult>, response: Response<UploadResult>) {
                var result = response.body()
                // if false then error isn't present
                if(!result!!.error){
                    callback.onSuccess(true)
                }
            }
        })

    }

    override fun uploadProduct(product: Product, callback: UploadCallBack<Boolean>) {
        //upload body didn't work when using @Body
        uploadcall = ApiClient.build()?.upload(product.name, product.encode, product.price, product.description)

        uploadcall?.enqueue(object : Callback<UploadResult>{
            override fun onFailure(call: Call<UploadResult>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(call: Call<UploadResult>, response: Response<UploadResult>) {
                var result = response.body()
                // if false then error isn't present
                if(!result!!.error){
                    callback.onSuccess(true)
                }
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