package com.frezzcoding.bolosassuncao.data

interface OperationCallBack<T> {
    fun onSuccess(data:ArrayList<T>?)
    fun onError(error:String?)
}