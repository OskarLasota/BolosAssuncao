package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface ProductDataSource {

    fun geneticOperation(operation : Int, product : Product, callback : UploadCallBack<Boolean>)
    fun retrieveProducts(callback : OperationCallBack<Product>)
}