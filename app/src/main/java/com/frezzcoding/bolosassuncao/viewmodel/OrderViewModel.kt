package com.frezzcoding.bolosassuncao.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frezzcoding.bolosassuncao.data.OperationCallBack
import com.frezzcoding.bolosassuncao.data.UploadCallBack
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.OrderDataSource
import com.frezzcoding.bolosassuncao.models.Product

class OrderViewModel(private val repository : OrderDataSource) : ViewModel() {

    private val _orders = MutableLiveData<ArrayList<Order>>()
    val orders : LiveData<ArrayList<Order>> = _orders

    private val _upload = MutableLiveData<Int>()
    val upload : LiveData<Int> = _upload

    private val _productupload = MutableLiveData<Boolean>()
    val productupload : LiveData<Boolean> = _productupload

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList : LiveData<Boolean> = _isEmptyList

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val OPERATION_UPLOAD = 1

    fun upload(order: Order){
        _isViewLoading.postValue(true)
        repository.geneticOperation(OPERATION_UPLOAD, order, object: UploadCallBack<Int> {
            override fun onSuccess(data: Int) {
                _isViewLoading.postValue(false)
                _upload.value = data
            }

            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

        })
    }

    fun upload(productid: Int, orderid : Int){
        _isViewLoading.postValue(true)
        repository.uploadOrderProducts(productid, orderid, object: UploadCallBack<Boolean> {
            override fun onSuccess(data: Boolean) {
                _isViewLoading.postValue(false)
                _productupload.value = data
            }

            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

        })
    }


    fun getOrders(){
        _isViewLoading.postValue(true)
        repository.retrieveOrders(object: OperationCallBack<Order> {
            override fun onSuccess(data: ArrayList<Order>) {
                _isViewLoading.postValue(false)
                if(data.isEmpty()){
                    _isEmptyList.postValue(true)
                }else{
                    _orders.value = data
                }

            }

            override fun onError(error: String?) {
                println(error)
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

        })
    }



}