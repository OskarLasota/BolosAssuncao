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
import kotlin.math.absoluteValue

class OrderRepository : OrderDataSource {
    private var call: Call<ArrayList<Order>> ?= null
    private var genericCall : Call<Int>?= null

    private val OPERATION_UPLOAD = 1

    override fun uploadOrderProducts(productId: Int, orderId: Int, callback: UploadCallBack<Boolean>) {

    }


    override fun geneticOperation(operation: Int, order: Order, callback: UploadCallBack<Int>) {
        if(operation ==OPERATION_UPLOAD) {
            genericCall = ApiClient.build()?.submit_order(
                order.id,
                order.user_id,
                order.customer_name,
                order.delivery_time,
                order.delivery_date,
                order.mobile,
                order.address_1,
                order.address_2,
                order.postcode,
                order.delivery_collection,
                order.instructions,
                order.payment_type,
                order.status
            )
        }
        genericCall?.enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                response?.body()?.let{
                    if(response.isSuccessful){
                        callback.onSuccess(it)
                    }
                }
            }

        })
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