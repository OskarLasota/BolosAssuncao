package com.frezzcoding.bolosassuncao.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.repo.BasketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BasketViewModel(var _application: Application) : AndroidViewModel(_application) {

    private var repository : BasketRepository? = null
    private var _basket = MutableLiveData<List<Product>>()
    var basket : LiveData<List<Product>> = _basket

    private var _loading = MutableLiveData<Boolean>()
    var loading : LiveData<Boolean> = _loading


    fun init(){
        repository = BasketRepository(_application)
        basket = repository!!.getProducts()
    }

    fun delete(product : Product) = viewModelScope.launch(Dispatchers.IO){
        _loading.postValue(true)
        repository?.delete(product)
        _loading.postValue(false)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        _loading.postValue(true)
        repository?.deleteAll()
        _loading.postValue(false)
    }

    fun insert(product : Product) = viewModelScope.launch(Dispatchers.IO) {
        _loading.postValue(true)
        repository?.insert(product)
        _loading.postValue(false)
    }

}