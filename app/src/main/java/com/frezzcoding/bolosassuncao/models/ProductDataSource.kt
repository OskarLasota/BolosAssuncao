package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.OperationCallBack

interface ProductDataSource {

    fun retrieveProducts(callback : OperationCallBack<Product>)
    fun cancel()
}