package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface OrderDataSource {

    fun geneticOperation(operation : Int, Order : Order, callback : UploadCallBack<Boolean>)
    fun retrieveOrders(callback : OperationCallBack<Product>)

}