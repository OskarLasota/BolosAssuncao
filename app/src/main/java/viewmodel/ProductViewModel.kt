package viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import models.Product
import repo.ProductRepository


class ProductViewModel(application : Application) : ViewModel() {

    private var repository : ProductRepository = ProductRepository().getInstance()
    private lateinit var products : LiveData<Product>

    init {
        products = repository.initProducts()
    }


    private fun getProducts() = repository.getProducts()

    private fun addProduct(product : Product) = repository.addProduct(product)

    private fun updateProduct(product : Product) = repository.updateProduct(product)





}