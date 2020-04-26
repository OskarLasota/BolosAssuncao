package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface ProductDataSource {

    fun updateProduct(product : Product, callback: UploadCallBack<Boolean>)
    fun uploadProduct(product: Product, callback: UploadCallBack<Boolean>)
    fun retrieveProducts(callback : OperationCallBack<Product>)
    fun cancel()
}