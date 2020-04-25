package com.frezzcoding.bolosassuncao.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.models.Product
import com.squareup.picasso.Picasso

class ViewAdapter(private val context : Context, private val _data : List<Product>, var listener : OnItemClickListener) : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var _view : View
        var inflater : LayoutInflater = LayoutInflater.from(context)
        _view = inflater.inflate(R.layout.cardview, parent, false)
        return ViewHolder(_view)

    }

    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product : Product = _data[position]
        holder.bind(product, listener)
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image : ImageView? = null
        private var tvTitle : TextView? = null
        private lateinit var product : Product
        private lateinit var _listener : OnItemClickListener
        init{
            image = itemView.findViewById(R.id.imageview_id)
            tvTitle = itemView.findViewById(R.id.image_title)

            itemView.setOnClickListener {
                _listener.onItemClick(product)
            }

        }

        fun bind(_product : Product, listener : OnItemClickListener){
            tvTitle?.text = _product.name
            _listener = listener
            product = _product
            //set image using picasso library
            Picasso.get().load(_product.url).into(image)
        }


    }

    interface OnItemClickListener {
        fun onItemClick(product : Product)
        //fun onLongPressClickListener(product : Product)
    }

}