package com.frezzcoding.bolosassuncao.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.BasketCardviewBinding
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.BasketViewModel
import com.squareup.picasso.Picasso

class BasketViewAdapter(private val _data : List<Product>, private val viewModel : BasketViewModel) : RecyclerView.Adapter<BasketViewAdapter.ViewHolder>(){

    private lateinit var binding : BasketCardviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.basket_cardview, parent, false)

        return ViewHolder(binding, viewModel)

    }


    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product : Product = _data[position]
        holder.bind(product)
    }


    class ViewHolder(private val binding: BasketCardviewBinding, private val viewModel : BasketViewModel) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var product : Product

        init{
            binding.ivRemove.setOnClickListener {
                viewModel.delete(product)
            }
        }

        fun bind(_product : Product){
            binding.product = _product
            product = _product
            //set image using picasso library, try to add this code in the model so we can databind
            Picasso.get().load(_product.url).into(binding.imageviewId)
        }


    }

}