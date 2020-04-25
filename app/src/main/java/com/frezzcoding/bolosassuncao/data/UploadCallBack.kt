package com.frezzcoding.bolosassuncao.data

interface UploadCallBack<T> {
    //onsuccess needs to be made generic somehow
    fun onSuccess(data: T)
    fun onError(error:String?)
}