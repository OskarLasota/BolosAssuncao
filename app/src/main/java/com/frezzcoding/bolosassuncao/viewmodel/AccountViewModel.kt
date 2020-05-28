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

    private val _usernames = MutableLiveData<ArrayList<User>>()
    val usernames : LiveData<ArrayList<User>> = _usernames

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<String>()
    val onMessageError:LiveData<String> = _onMessageError


    private val OPERATION_LOGIN = 0;
    private val OPERATION_REGISTER = 1;

    fun getUser(input : User){
        _isViewLoading.postValue(true)
        repository.genericOperation(OPERATION_LOGIN, input, object: UploadCallBack<User> {
            override fun onSuccess(data: User) {
                _isViewLoading.postValue(false)
                _user.value = data

            }

            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

        })
    }

    fun getUsernames(){
        repository.usernameOperation(object: UploadCallBack<ArrayList<User>>{
            override fun onSuccess(data: ArrayList<User>) {
                _usernames.value = data
            }

            override fun onError(error: String?) {
                _onMessageError.postValue(error)
            }

        })
    }

    fun registerUser(input : User){
        _isViewLoading.postValue(true)
        repository.genericOperation(OPERATION_REGISTER, input, object: UploadCallBack<User> {
            override fun onSuccess(data: User) {
                _isViewLoading.postValue(false)
                _user.value = data

            }

            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

        })
    }


}