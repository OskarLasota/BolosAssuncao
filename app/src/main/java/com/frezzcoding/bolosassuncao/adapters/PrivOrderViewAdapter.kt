package com.frezzcoding.bolosassuncao.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.PrivOrderCardviewBinding
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModel
import com.squareup.picasso.Picasso

class PrivOrderViewAdapter(private val _data : List<Order>, private val viewModel : OrderViewModel) : RecyclerView.Adapter<PrivOrderViewAdapter.ViewHolder>(){

    private lateinit var binding : PrivOrderCardviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.priv_order_cardview, parent, false)

        return ViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order : Order = _data[position]
        holder.bind(order)
    }


    class ViewHolder(private val binding: PrivOrderCardviewBinding, private val viewModel : OrderViewModel) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var order : Order

        init{
            /*
            binding.ivRemove.setOnClickListener {
                viewModel.delete(order)
            }

             */
        }

        fun bind(_order : Order){
            binding.order = _order
            order = _order
            //set image using picasso library, try to add this code in the model so we can databind
            //Picasso.get().load(_order.url).into(binding.imageviewId)
        }


    }


}