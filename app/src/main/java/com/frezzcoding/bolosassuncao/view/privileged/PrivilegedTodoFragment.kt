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
import com.frezzcoding.bolosassuncao.adapters.TodoViewAdapter
import com.frezzcoding.bolosassuncao.data.OrdersOverviewResult
import com.frezzcoding.bolosassuncao.databinding.FragmentTodoBinding
import com.frezzcoding.bolosassuncao.di.OrderInjection
import com.frezzcoding.bolosassuncao.di.ProductInjection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModel
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel
import com.frezzcoding.bolosassuncao.viewmodel.ViewModelFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PrivilegedTodoFragment : Fragment() {
    private lateinit var binding : FragmentTodoBinding
    private lateinit var orderViewModel : OrderViewModel
    var productList = HashMap<String, Int>()
    private lateinit var adapterTodo : TodoViewAdapter
    private lateinit var calendar : Calendar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_todo, container, false
        )

        var dt = Date()
        calendar = Calendar.getInstance()
        calendar.time = dt

        initializeViewModel()

        return binding.root
    }

    private fun initializeViewModel(){
        orderViewModel = ViewModelProvider(this, OrderInjection.provideViewModelFactory()).get(OrderViewModel::class.java)
        orderViewModel.ordersoverview.observe(viewLifecycleOwner, obtainOrders)
        orderViewModel.getOrderOverview()
    }

    private val obtainOrders = Observer<ArrayList<OrdersOverviewResult>>{
        productList = HashMap<String, Int>()
        for(order in it){
            if(productList[order.url] == null){
                productList[order.url] = 0
            }
            if(order.delivery_date.contains(calendar.time.toString().substring(0,10))){
                productList[order.url] = productList[order.url]!! + 1
            }
        }
        adapterTodo = TodoViewAdapter(productList)
        binding.recyclerView.layoutManager = GridLayoutManager(this.requireContext(), 1)
        binding.recyclerView.adapter = adapterTodo

    }


}