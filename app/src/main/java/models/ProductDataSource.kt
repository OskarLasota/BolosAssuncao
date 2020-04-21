package models

import data.OperationCallBack

interface ProductDataSource {

    fun retrieveProducts(callback : OperationCallBack<Product>)
    fun cancel()
}