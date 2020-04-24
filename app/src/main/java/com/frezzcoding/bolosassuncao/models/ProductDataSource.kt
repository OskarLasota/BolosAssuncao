package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.OperationCallBack

interface ProductDataSource {

    fun uploadProduct(product : Product, callback : OperationCallBack<Boolean>)
    fun retrieveProducts(callback : OperationCallBack<Product>)
    fun cancel()
}