package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.adapters.BasketViewAdapter
import com.frezzcoding.bolosassuncao.adapters.OrderViewAdapter
import com.frezzcoding.bolosassuncao.databinding.FragmentBasketBinding
import com.frezzcoding.bolosassuncao.di.OrderInjection
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.BasketViewModel
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModel

class NeutralBasketFragment : Fragment() {

    private lateinit var viewModel : BasketViewModel
    private lateinit var ordersViewModel : OrderViewModel
    private lateinit var adapterProduct : BasketViewAdapter
    private var productList : List<Product> = ArrayList()
    private lateinit var binding : FragmentBasketBinding
    private var allowOrder = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_basket, container, false
        )

        if(activity is NeutralUserActivity){
            (activity as NeutralUserActivity)?.hideBottombar(true)
        }

        initializeViewModel()
        ordersViewModel.getOrders()
        setObservers()
        return binding.root
    }


    private fun setObservers(){
        var bundle : Bundle
        binding.btnCollection.setOnClickListener {
            if(productList.isNotEmpty() && allowOrder) {
                bundle = bundleOf("products" to productList)
                Navigation.findNavController(binding.root)
                    .navigate(R.id.destination_collection, bundle)
            }
        }
        binding.btnDelivery.setOnClickListener {
            if(productList.isNotEmpty() && allowOrder) {
                bundle = bundleOf("products" to productList)
                Navigation.findNavController(binding.root)
                    .navigate(R.id.destination_delivery, bundle)
            }
        }
    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider.AndroidViewModelFactory(activity!!.application).create(BasketViewModel(activity!!.application).javaClass)
        viewModel.init()
        viewModel.loading.observe(viewLifecycleOwner, observeLoading)
        viewModel.basket.observe(viewLifecycleOwner, observeBasket)

        ordersViewModel = ViewModelProvider(this, OrderInjection.provideViewModelFactory()).get(
            OrderViewModel::class.java)
        ordersViewModel.orders.observe(viewLifecycleOwner, renderOrders)
    }

    private val renderOrders = Observer<ArrayList<Order>>{
        if(it.size > 2){
            allowOrder = false
            Toast.makeText(this.context, "You have placed too many orders", Toast.LENGTH_SHORT).show()
        }
    }

    private val observeBasket = Observer<List<Product>>{
        productList = it
        adapterProduct = BasketViewAdapter(productList, viewModel)
        binding.ordersRecycler.layoutManager = GridLayoutManager(this.requireContext(), 1)
        binding.ordersRecycler.adapter = adapterProduct
        var sum = 0.0
        for(product in it){
            sum += product.price
        }
        binding.tvLabelsubtotal.text = "R$ " + String.format("%.2f", sum)
    }

    private val observeLoading = Observer<Boolean>{
        if(it){
            println("loading")
        }else{
            println("loading complete")
        }
    }


}