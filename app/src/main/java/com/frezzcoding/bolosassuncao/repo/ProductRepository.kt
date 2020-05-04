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



class ProductRepository : ProductDataSource {

    private var call: Call<ArrayList<Product>> ?= null
    private var genericCall : Call<UploadResult> ?= null


    private val OPERATION_DELETE = 0;
    private val OPERATION_UPDATE = 1;
    private val OPERATION_UPLOAD = 2;


    override fun geneticOperation(operation : Int, product : Product, callback: UploadCallBack<Boolean>){
        when(operation){
            OPERATION_DELETE -> genericCall = ApiClient.build()?.delete(product.id)
            OPERATION_UPDATE -> genericCall = ApiClient.build()?.update(product.id, product.name, product.encode, product.price, product.description)
            OPERATION_UPLOAD -> genericCall = ApiClient.build()?.upload(product.name, product.encode, product.price, product.description)
            else -> callback.onError("wrong operation number")
        }

        genericCall?.enqueue(object : Callback<UploadResult>{
            override fun onFailure(call: Call<UploadResult>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<UploadResult>, response: Response<UploadResult>) {
                response.body()?.let{
                    if(response.isSuccessful) {
                        if (it!!.error == null) {
                            callback.onSuccess(true)
                        } else {
                            callback.onError(it!!.error)
                        }
                    }
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
                        callback.onError("Problem connecting to server")
                    }
                }
            }

        })
    }






}