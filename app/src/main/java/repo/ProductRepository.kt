package repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import models.Product

class ProductRepository() {

    private lateinit var instance : ProductRepository
    private lateinit var products : MutableLiveData<Product>

    fun getInstance() : ProductRepository {
        if(instance == null){
            instance = ProductRepository();
        }
        return instance;
    }

    fun updateProduct(product : Product){
        //call the api to update product
    }

    fun addProduct(product : Product){
        //call the api to add product
    }

    fun getProducts() {
        //call the api to get all products including pictures
    }

    fun initProducts() : MutableLiveData<Product>{
        products = MutableLiveData()
        return products
    }

}