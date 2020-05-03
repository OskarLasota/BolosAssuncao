package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface UserDataSource {

    fun retrieveUser(user: User, callback: UploadCallBack<User>)
    fun cancel()
}
