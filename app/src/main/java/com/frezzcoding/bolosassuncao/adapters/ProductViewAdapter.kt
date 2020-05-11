package com.frezzcoding.bolosassuncao.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.CardviewBinding
import com.frezzcoding.bolosassuncao.models.Product
import com.squareup.picasso.Picasso

class ProductViewAdapter(private val _data : List<Product>, var listener : OnItemClickListener) : RecyclerView.Adapter<ProductViewAdapter.ViewHolder>() {

    private lateinit var binding : CardviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.cardview, parent, false)

        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product : Product = _data[position]
        holder.bind(product, listener)
    }



    class ViewHolder(binding: CardviewBinding) : RecyclerView.ViewHolder(binding.root) {
        private var image : ImageView? = null
        private var tvTitle : TextView? = null
        private lateinit var product : Product
        private lateinit var _listener : OnItemClickListener
        private var _binding : CardviewBinding
        init{
            image = itemView.findViewById(R.id.imageview_id)
            tvTitle = itemView.findViewById(R.id.image_title)
            _binding = binding
            itemView.setOnClickListener {
                _listener.onItemClick(product)
            }

        }

        fun bind(_product : Product, listener : OnItemClickListener){
            _binding.product = _product
            _listener = listener
            product = _product
            //set image using picasso library, try to add this code in the model so we can databind
            Picasso.get().load(_product.url).into(image)
        }


    }

    interface OnItemClickListener {
        fun onItemClick(product : Product)
        //fun onLongPressClickListener(product : Product)
    }

}