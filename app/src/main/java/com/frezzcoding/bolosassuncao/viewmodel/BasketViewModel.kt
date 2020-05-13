package com.frezzcoding.bolosassuncao.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.frezzcoding.bolosassuncao.models.Basket
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.repo.BasketRepository

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

}