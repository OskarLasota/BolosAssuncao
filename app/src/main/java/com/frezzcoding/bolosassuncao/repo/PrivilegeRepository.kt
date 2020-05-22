package com.frezzcoding.bolosassuncao.repo

import com.frezzcoding.bolosassuncao.data.ApiClient
import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack
import com.frezzcoding.bolosassuncao.data.UploadResult
import com.frezzcoding.bolosassuncao.models.PrivilegeDataSource
import com.frezzcoding.bolosassuncao.models.Privileged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrivilegeRepository: PrivilegeDataSource {
    private var call: Call<Privileged> ?= null
    private var genericCall : Call<UploadResult>?= null

    override fun geneticOperation(operation: Int, priv: Privileged, callback: UploadCallBack<Boolean>) {
        TODO("Not yet implemented")
    }


    override fun retrievePrivileged(callback: UploadCallBack<Privileged>) {
        call= ApiClient.build()?.privileged()
        call?.enqueue(object : Callback<Privileged> {
            override fun onFailure(call: Call<Privileged>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<Privileged>, response: Response<Privileged>) {
                response?.body()?.let {
                    if (response.isSuccessful){
                        var list : Privileged = response.body()!!
                        callback.onSuccess(list)
                    }else{
                        callback.onError("Problem connecting to server")
                    }
                }
            }

        })
    }
}