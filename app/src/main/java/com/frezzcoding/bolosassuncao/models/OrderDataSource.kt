package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.OrdersOverviewResult
import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface OrderDataSource {

    fun uploadOrderProducts(productId : Int, orderId : Int, callback : UploadCallBack<Boolean>)
    fun geneticOperation(operation : Int, order : Order, callback : UploadCallBack<Int>)
    fun statusOperation(status : String, order : Order, callback : UploadCallBack<Int>)
    fun retrieveOrders(callback : OperationCallBack<Order>)
    fun retrieveOrdersOverview(operation : Int, callback : OperationCallBack<OrdersOverviewResult>)

}