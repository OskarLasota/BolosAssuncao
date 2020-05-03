package com.frezzcoding.bolosassuncao.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frezzcoding.bolosassuncao.data.UploadCallBack

import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.models.UserDataSource

class AccountViewModel(private val repository : UserDataSource) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError


    fun getUser(){
        _isViewLoading.postValue(true)
        repository.retrieveUser(object: UploadCallBack<User> {
            override fun onSuccess(data: User) {
                _isViewLoading.postValue(false)


            }

            override fun onError(error: String?) {
                println(error)
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

        })
    }

}