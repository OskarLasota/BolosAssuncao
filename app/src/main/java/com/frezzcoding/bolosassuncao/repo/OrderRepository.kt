package com.frezzcoding.bolosassuncao.repo

import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.OrderDataSource
import com.frezzcoding.bolosassuncao.models.Product

class OrderRepository : OrderDataSource {
    override fun geneticOperation(operation: Int, Order: Order, callback: UploadCallBack<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun retrieveOrders(callback: OperationCallBack<Order>) {
        TODO("Not yet implemented")
    }
}