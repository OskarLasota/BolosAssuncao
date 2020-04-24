package com.frezzcoding.bolosassuncao.data

import com.frezzcoding.bolosassuncao.models.Product

interface OperationCallBack<T> {

    //onsuccess needs to be made generic somehow
    fun onSuccess(data: ArrayList<Product>)
    fun onError(error:String?)
}