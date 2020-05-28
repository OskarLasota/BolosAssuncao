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
    private var call: Call<ArrayList<Privileged>> ?= null
    private var genericCall : Call<UploadResult>?= null

    override fun geneticOperation(operation: Int, priv: Privileged, callback: UploadCallBack<Boolean>) {
        genericCall = ApiClient.build()?.updateTimetable(priv.monday, priv.tuesday, priv.wednesday, priv.thursday, priv.friday, priv.saturday, priv.sunday, priv.start_time, priv.end_time)
        genericCall?.enqueue(object: Callback<UploadResult>{
            override fun onFailure(call: Call<UploadResult>, t: Throwable) {
                callback.onError(t.message)
            }
            override fun onResponse(call: Call<UploadResult>, response: Response<UploadResult>) {
                response.body().let{
                    if(response.body()?.error.equals("false")){
                        callback.onSuccess(true)
                    }else{
                        callback.onSuccess(false)
                    }
                }
                response.raw().close()
            }

        })
    }



    override fun retrievePrivileged(callback: OperationCallBack<Privileged>) {
        call= ApiClient.build()?.privileged()
        call?.enqueue(object : Callback<ArrayList<Privileged>> {
            override fun onFailure(call: Call<ArrayList<Privileged>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<ArrayList<Privileged>>, response: Response<ArrayList<Privileged>>) {
                response.body()?.let {
                    if (response.isSuccessful){
                        var list : ArrayList<Privileged> = response.body()!!
                        callback.onSuccess(list)
                    }else{
                        callback.onError("Problem connecting to server")
                    }
                    response.raw().close()
                }
            }

        })
    }
}