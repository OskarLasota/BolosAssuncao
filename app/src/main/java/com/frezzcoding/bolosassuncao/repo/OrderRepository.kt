package com.frezzcoding.bolosassuncao.repo

import com.frezzcoding.bolosassuncao.data.*
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
    private var productCall : Call<Boolean>?= null
    private var ordersCall : Call<ArrayList<OrdersOverviewResult>>?= null
    private var statusCall : Call<Int>?= null

    private val OPERATION_UPLOAD = 1
    private val OPERATION_DELETE = 3

    override fun uploadOrderProducts(productId: Int, orderId: Int, callback: UploadCallBack<Boolean>) {
        productCall = ApiClient.build()?.submit_order_product(productId, orderId)
        productCall?.enqueue(object: Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                callback.onError(t.message)
            }
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                response.body().let {
                    if(response.isSuccessful){
                        callback.onSuccess(true)
                    }
                }
            }

        })

    }


    override fun geneticOperation(operation: Int, order: Order, callback: UploadCallBack<Int>) {
        if(operation ==OPERATION_UPLOAD) {
            genericCall = ApiClient.build()?.submit_order(
                order.id,
                order.user_id,
                order.cost,
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
        if(operation == OPERATION_DELETE){
            genericCall = ApiClient.build()?.deleteOrder(order.id)
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

    override fun statusOperation(status: String, order: Order, callback: UploadCallBack<Int>) {
        statusCall = ApiClient.build()?.update_order_status(status, order.id)
        statusCall?.enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                response?.body().let{
                    if(response.isSuccessful){
                        if (it != null) {
                            callback.onSuccess(it)
                        }
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

    override fun retrieveOrdersOverview(operation: Int, callback: OperationCallBack<OrdersOverviewResult>) {
        ordersCall = ApiClient.build()?.order_overview()
        ordersCall?.enqueue(object: Callback<ArrayList<OrdersOverviewResult>>{
            override fun onFailure(call: Call<ArrayList<OrdersOverviewResult>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<ArrayList<OrdersOverviewResult>>, response: Response<ArrayList<OrdersOverviewResult>>) {
                response?.body()?.let {
                    if(response.isSuccessful){
                        var list : ArrayList<OrdersOverviewResult> = response.body()!!
                        callback.onSuccess(list)
                    }else{
                        callback.onError("Problem connecting to server")
                    }
                }
            }

        })
    }
}