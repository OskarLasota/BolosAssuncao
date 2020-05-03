package com.frezzcoding.bolosassuncao.repo

import com.frezzcoding.bolosassuncao.data.ApiClient
import com.frezzcoding.bolosassuncao.data.UploadCallBack
import com.frezzcoding.bolosassuncao.data.UserResult
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.models.UserDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository : UserDataSource {

    private var call: Call<UserResult>?= null

    override fun retrieveUser(user: User, callback: UploadCallBack<User>) {
        call = ApiClient.build()?.login(user.getUsername(), user.getPassword())

        call?.enqueue(object : Callback<UserResult> {
            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<UserResult>, response: Response<UserResult>) {
                var result = response.body()
                if(result!!.error){
                    callback.onSuccess(User(result.id))
                }else{
                    callback.onError("something went wrong")
                }
            }

        })

    }

    override fun cancel() {

    }
}