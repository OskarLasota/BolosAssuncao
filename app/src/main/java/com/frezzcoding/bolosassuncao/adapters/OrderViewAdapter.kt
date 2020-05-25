package com.frezzcoding.bolosassuncao.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.OrderCardviewBinding
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModel

class OrderViewAdapter(private val _data : List<Order>, private val viewModel : OrderViewModel) : RecyclerView.Adapter<OrderViewAdapter.ViewHolder>(){

    private lateinit var binding : OrderCardviewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.order_cardview, parent, false)

        return ViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order : Order = _data[position]
        holder.bind(order)
    }



    class ViewHolder(private val binding: OrderCardviewBinding, private val viewModel : OrderViewModel) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var order : Order

        init{
            binding.ivRemove.setOnClickListener {
                viewModel.delete(order)
            }
        }

        fun bind(_order : Order){
            binding.order = _order
            order = _order
        }


    }

}