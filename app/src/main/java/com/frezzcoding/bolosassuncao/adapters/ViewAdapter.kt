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

class ViewAdapter(private val context : Context, private val _data : List<Product>) : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var _view : View
        var inflater : LayoutInflater = LayoutInflater.from(context)
        _view = inflater.inflate(R.layout.cardview, parent, false)
        return ViewHolder(_view)

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product : Product = _data[position]
        holder.bind(product)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image : ImageView? = null
        private var tvTitle : TextView? = null

        init{
            image = itemView.findViewById(R.id.imageview_id)
            tvTitle = itemView.findViewById(R.id.image_title)
        }

        fun bind(product : Product){
            tvTitle?.text = product.name
            //image?.setImageURI(product.uri)
        }


    }

}