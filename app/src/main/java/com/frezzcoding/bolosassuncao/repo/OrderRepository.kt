package com.frezzcoding.bolosassuncao.repo

import com.frezzcoding.bolosassuncao.data.ApiClient
import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack
import com.frezzcoding.bolosassuncao.data.UploadResult
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.OrderDataSource
import com.frezzcoding.bolosassuncao.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository : OrderDataSource {
    private var call: Call<ArrayList<Order>> ?= null
    private var genericCall : Call<UploadResult>?= null

    override fun geneticOperation(operation: Int, Order: Order, callback: UploadCallBack<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun retrieveOrders(callback: OperationCallBack<Order>) {
        call= ApiClient.build()?.orders()
        call?.enqueue(object : Callback<ArrayList<Order>> {
            override fun onFailure(call: Call<ArrayList<Order>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<ArrayList<Order>>, response: Response<ArrayList<Order>>) {
                response?.body()?.let {
                    if (response.isSuccessful){
                        var list : ArrayList<Order> = response.body()!!
                        callback.onSuccess(list)
                    }else{
                        callback.onError("Problem connecting to server")
                    }
                }
            }

        })
    }
}