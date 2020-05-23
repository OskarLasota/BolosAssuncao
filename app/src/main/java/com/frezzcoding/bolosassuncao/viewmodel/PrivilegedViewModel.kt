package com.frezzcoding.bolosassuncao.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frezzcoding.bolosassuncao.data.UploadCallBack
import com.frezzcoding.bolosassuncao.data.UploadResult
import com.frezzcoding.bolosassuncao.models.PrivilegeDataSource
import com.frezzcoding.bolosassuncao.models.Privileged
import com.frezzcoding.bolosassuncao.models.UserDataSource

class PrivilegedViewModel (private val repository : PrivilegeDataSource) : ViewModel() {

    private val _priv = MutableLiveData<Privileged>()
    val priv : LiveData<Privileged> = _priv

    private val _update = MutableLiveData<Boolean>()
    val update : LiveData<Boolean> = _update

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val OPERATION_UPDATE = 1;

    fun updateTimetable(temp : Privileged){
        _isViewLoading.postValue(true)
        repository.geneticOperation(OPERATION_UPDATE, temp, object : UploadCallBack<Boolean>{
            override fun onSuccess(data: Boolean) {
                _isViewLoading.postValue(false)
                _update.value = data
            }

            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                println(error)
            }

        })
    }

    fun getPrivileged(){
        _isViewLoading.postValue(true)
        repository.retrievePrivileged(object : UploadCallBack<Privileged>{
            override fun onSuccess(data: Privileged) {
                _isViewLoading.postValue(false)
                _priv.value = data
            }
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                println(error)
            }

        })
    }


}