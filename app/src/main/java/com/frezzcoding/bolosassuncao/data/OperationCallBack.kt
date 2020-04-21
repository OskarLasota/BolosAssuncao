package com.frezzcoding.bolosassuncao.data

interface OperationCallBack<T> {
    fun onSuccess(data:List<T>?)
    fun onError(error:String?)
}