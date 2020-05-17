package com.frezzcoding.bolosassuncao.view.privileged

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.adapters.PrivOrderViewAdapter
import com.frezzcoding.bolosassuncao.databinding.FragmentOrdersBinding
import com.frezzcoding.bolosassuncao.di.OrderInjection
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModel

class PrivilegedOrderFragment : Fragment() {
    private lateinit var viewModel : OrderViewModel
    private lateinit var binding : FragmentOrdersBinding
    private lateinit var adapterProduct : PrivOrderViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_orders, container, false
        )



        initializeViewModel()
        viewModel.getOrders()


        return binding.root
    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, OrderInjection.provideViewModelFactory()).get(OrderViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, renderOrders)
    }

    private val renderOrders = Observer<ArrayList<Order>>{
        ///productList = it
        adapterProduct = PrivOrderViewAdapter(it, viewModel)
        binding.ordersRecycler.layoutManager = GridLayoutManager(this.requireContext(), 1)
        binding.ordersRecycler.adapter = adapterProduct
        //println(it[0].customer_name)
        //make another call to get all products for that order
    }

}