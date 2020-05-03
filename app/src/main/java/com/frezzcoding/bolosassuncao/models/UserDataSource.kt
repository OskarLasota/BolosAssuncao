package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface UserDataSource {

    fun retrieveUser(callback : UploadCallBack<User>)
    fun cancel()
}
