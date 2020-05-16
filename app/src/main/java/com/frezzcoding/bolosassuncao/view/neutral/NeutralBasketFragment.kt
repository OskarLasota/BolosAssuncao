package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.adapters.BasketViewAdapter
import com.frezzcoding.bolosassuncao.databinding.FragmentBasketBinding
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.BasketViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_neutral.*
import kotlinx.android.synthetic.main.activity_neutral.bottom_nav

class NeutralBasketFragment : Fragment() {

    private lateinit var viewModel : BasketViewModel
    private lateinit var adapterProduct : BasketViewAdapter
    private var productList : List<Product> = ArrayList()
    private lateinit var binding : FragmentBasketBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_basket, container, false
        )

        if(activity is NeutralUserActivity){
            (activity as NeutralUserActivity)?.hideBottombar(true)
        }

        initializeViewModel()
        setObservers()
        return binding.root
    }


    private fun setObservers(){
        binding.btnCollection.setOnClickListener {

        }
        binding.btnDelivery.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.destination_delivery)
        }
    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider.AndroidViewModelFactory(activity!!.application).create(BasketViewModel(activity!!.application).javaClass)
        viewModel.init()
        viewModel.loading.observe(viewLifecycleOwner, observeLoading)
        viewModel.basket.observe(viewLifecycleOwner, observeBasket)
    }

    private val observeBasket = Observer<List<Product>>{
        productList = it
        adapterProduct = BasketViewAdapter(productList, viewModel)
        binding.ordersRecycler.layoutManager = GridLayoutManager(this.requireContext(), 1)
        binding.ordersRecycler.adapter = adapterProduct
        var sum = 0.0
        for(product in it){
            sum =+ product.price
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