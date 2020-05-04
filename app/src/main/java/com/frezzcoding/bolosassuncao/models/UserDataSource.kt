package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface UserDataSource {

    fun registerUser(user: User, callback: UploadCallBack<User>)
    fun retrieveUser(user: User, callback: UploadCallBack<User>)
}
