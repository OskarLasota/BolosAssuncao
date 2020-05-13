package com.frezzcoding.bolosassuncao.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.BasketCardviewBinding
import com.frezzcoding.bolosassuncao.models.Product
import com.squareup.picasso.Picasso

class BasketViewAdapter(private val _data : List<Product>) : RecyclerView.Adapter<BasketViewAdapter.ViewHolder>() {

    private lateinit var binding : BasketCardviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.basket_cardview, parent, false)

        return ViewHolder(binding)

    }


    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product : Product = _data[position]
        holder.bind(product)
    }


    class ViewHolder(binding: BasketCardviewBinding) : RecyclerView.ViewHolder(binding.root) {
        private var image : ImageView? = null
        private var tvTitle : TextView? = null
        private lateinit var product : Product
        private var _binding : BasketCardviewBinding
        init{
            image = itemView.findViewById(R.id.imageview_id)
            tvTitle = itemView.findViewById(R.id.image_title)
            _binding = binding


        }

        fun bind(_product : Product){
            _binding.product = _product
            product = _product
            //set image using picasso library, try to add this code in the model so we can databind
            Picasso.get().load(_product.url).into(image)
        }


    }

}