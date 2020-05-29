package com.frezzcoding.bolosassuncao.models

import com.frezzcoding.bolosassuncao.data.UploadCallBack

interface UserDataSource {

    fun usernameOperation(callback: UploadCallBack<ArrayList<User>>)
    fun genericOperation(operation : Int, user: User, callback: UploadCallBack<User>)
    fun tokenOperation(token : String, id : Int, callback: UploadCallBack<Int>)

}
