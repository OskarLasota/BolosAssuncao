package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface UserDataSource {

    fun genericOperation(operation : Int, user: User, callback: UploadCallBack<User>)

}
