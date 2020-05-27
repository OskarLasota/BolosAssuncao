package com.frezzcoding.bolosassuncao.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.TodoCardviewBinding
import com.squareup.picasso.Picasso

class TodoViewAdapter (private val _data : HashMap<String, Int>) : RecyclerView.Adapter<TodoViewAdapter.ViewHolder>(){

    private lateinit var binding : TodoCardviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.todo_cardview, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keys = ArrayList(_data.keys)
        val values = ArrayList(_data.values)
        holder.bind(keys[position], values[position])
    }


    class ViewHolder(private val binding: TodoCardviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url : String, amount : Int){
            println("`url ` "+ url)
            Picasso.get().load(url).into(binding.imageviewId)
            binding.tvAmount.text = amount.toString()
        }


    }


}