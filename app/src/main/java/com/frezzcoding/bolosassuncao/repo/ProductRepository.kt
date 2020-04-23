package com.frezzcoding.bolosassuncao.repo

import com.frezzcoding.bolosassuncao.data.ApiClient
import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.models.ProductDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository() : ProductDataSource {

    private var call: Call<ArrayList<Product>>?= null

    override fun retrieveProducts(callback: OperationCallBack<Product>) {
        call= ApiClient.build()?.products()
        call?.enqueue(object : Callback<ArrayList<Product>>{
            override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<ArrayList<Product>>, response: Response<ArrayList<Product>>) {
                response?.body()?.let {
                    if (response.isSuccessful){
                        println(response.body())
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