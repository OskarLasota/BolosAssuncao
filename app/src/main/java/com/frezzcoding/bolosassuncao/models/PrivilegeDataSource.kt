package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface PrivilegeDataSource {

    fun geneticOperation(operation : Int, priv : Privileged, callback : UploadCallBack<Boolean>)
    fun retrievePrivileged(callback : UploadCallBack<Privileged>)

}