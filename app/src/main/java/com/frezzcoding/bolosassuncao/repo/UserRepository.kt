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

    private var genericCall : Call<UserResult>?= null
    private var usernameCall : Call<ArrayList<User>>?= null
    private val OPERATION_LOGIN = 0;
    private val OPERATION_REGISTER = 1;


    override fun usernameOperation(callback: UploadCallBack<ArrayList<User>>) {
        usernameCall = ApiClient.build()?.usernames()
        usernameCall?.enqueue(object: Callback<ArrayList<User>>{
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                response.body().let{
                    if(response.isSuccessful){
                        if (it != null) {
                            callback.onSuccess(it)
                        }
                    }
                }
            }

        })
    }


    override fun genericOperation(operation : Int, user:User, callback: UploadCallBack<User>){
        when(operation){
            OPERATION_LOGIN -> genericCall = ApiClient.build()?.login(user.username, user.password)
            OPERATION_REGISTER -> genericCall = ApiClient.build()?.register(user.username, user.password, user.email)
            else -> callback.onError("technical error")
        }

        genericCall?.enqueue(object: Callback<UserResult>{
            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<UserResult>, response: Response<UserResult>) {
               response.body().let {
                   if(response.isSuccessful){
                       //i dont like this error check, need to fix backend
                       if(it!!.error == null){
                           callback.onSuccess(User(it.id, it.privilege))
                       }else{
                           callback.onError(it!!.error)
                       }
                   }
               }
            }

        })
    }



}