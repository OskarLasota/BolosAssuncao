package viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.OperationCallBack
import models.Product
import models.ProductDataSource
import repo.ProductRepository


class ProductViewModel(private val repository : ProductDataSource) : ViewModel() {

    //private val _products = MutableLiveData<List<Product>>().apply { value = emptyList() }
    private val _products = MutableLiveData<List<Product>>()
    val products : LiveData<List<Product>> = _products

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList


    fun getProducts(){
        _isViewLoading.postValue(true)
        repository.retrieveProducts(object:OperationCallBack<Product>{
            override fun onSuccess(data: List<Product>?) {
                _isViewLoading.postValue(false)

                if(data!=null){
                    if(data.isEmpty()){
                        println("reached is empty")
                        _isEmptyList.postValue(true)
                    }else{
                        _products.value = data
                    }
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