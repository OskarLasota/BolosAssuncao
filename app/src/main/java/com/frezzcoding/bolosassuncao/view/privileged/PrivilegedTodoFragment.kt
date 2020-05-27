package com.frezzcoding.bolosassuncao.view.privileged

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.data.OrdersOverviewResult
import com.frezzcoding.bolosassuncao.databinding.FragmentTodoBinding
import com.frezzcoding.bolosassuncao.di.OrderInjection
import com.frezzcoding.bolosassuncao.di.ProductInjection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModel
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel
import com.frezzcoding.bolosassuncao.viewmodel.ViewModelFactory

class PrivilegedTodoFragment : Fragment() {
    private lateinit var binding : FragmentTodoBinding
    private lateinit var viewModel : ProductViewModel
    private lateinit var orderViewModel : OrderViewModel
    var productList = HashMap<String, Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_todo, container, false
        )

        //initializeViewModel()
        /*
        todo
        this fragment will present the products that have a due date of the current day to inform the baker
        which cakes need to be baked
        todo show current date
        todo get list of products
        todo put cardview with the product image
        todo need to create a backend script that will provide cakes for current day or will provide product_id with each order call
         */

        initializeViewModel()


        return binding.root
    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, ProductInjection.provideViewModelFactory()).get(ProductViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, listenProducts)
        viewModel.getProducts()

        orderViewModel = ViewModelProvider(this, OrderInjection.provideViewModelFactory()).get(OrderViewModel::class.java)
        orderViewModel.ordersoverview.observe(viewLifecycleOwner, obtainOrders)
        orderViewModel.getOrderOverview()

    }

    private val obtainOrders = Observer<ArrayList<OrdersOverviewResult>>{
        println(it.size)
        println("reached")
    }

    private val listenProducts = Observer<ArrayList<Product>>{
        for(product in it){
            productList[product.name] = 0
        }
    }

}