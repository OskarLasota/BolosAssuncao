package com.frezzcoding.bolosassuncao.adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.PrivOrderCardviewBinding
import com.frezzcoding.bolosassuncao.models.Order
import com.frezzcoding.bolosassuncao.viewmodel.OrderViewModel


class PrivOrderViewAdapter(private val _data : List<Order>, private val viewModel : OrderViewModel) : RecyclerView.Adapter<PrivOrderViewAdapter.ViewHolder>(){

    private lateinit var binding : PrivOrderCardviewBinding
    private lateinit var ctx : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ctx = parent.context
        var inflater : LayoutInflater = LayoutInflater.from(ctx)
        binding = DataBindingUtil.inflate(inflater, R.layout.priv_order_cardview, parent, false)

        return ViewHolder(binding, viewModel, ctx)
    }

    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order : Order = _data[position]
        holder.bind(order)
    }


    class ViewHolder(private val binding: PrivOrderCardviewBinding, private val viewModel : OrderViewModel, private val ctx : Context) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var order : Order
        private var dialog = Dialog(ctx)

        init{
            binding.cardview.setOnClickListener {
                var number = "+447773117678"
                val url = "https://api.whatsapp.com/send?phone=${order.mobile}"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                ctx.startActivity(i)
            }

            binding.arrowBtn.setOnClickListener {
                if(!binding.expandable.isVisible){
                    TransitionManager.beginDelayedTransition(binding.cardview, AutoTransition())
                    binding.expandable.visibility = View.VISIBLE
                    //arrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
                }else{
                    TransitionManager.beginDelayedTransition(binding.cardview, AutoTransition())
                    binding.expandable.visibility = View.GONE
                }
            }

            binding.ivStatus.setOnClickListener {
                dialog.setContentView(R.layout.popup_select_status)
                var btn1 = dialog.findViewById<Button>(R.id.btn1)
                var btn2 = dialog.findViewById<Button>(R.id.btn2)
                var btn3 = dialog.findViewById<Button>(R.id.btn3)
                var btn4 = dialog.findViewById<Button>(R.id.btn4)
                var btn5 = dialog.findViewById<Button>(R.id.btn5)
                btn1.setOnClickListener { onClick(btn1) }
                btn2.setOnClickListener { onClick(btn2) }
                btn3.setOnClickListener { onClick(btn3) }
                btn4.setOnClickListener { onClick(btn4) }
                btn5.setOnClickListener { onClick(btn5) }

                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            }


        }

        fun bind(_order : Order){
            binding.order = _order
            order = _order
            //set image using picasso library, try to add this code in the model so we can databind
            //Picasso.get().load(_order.url).into(binding.imageviewId)
        }

        private fun onClick(v: View?) {
            when(v?.id){
                R.id.btn1 -> viewModel.setStatus("Order Accepted", order)
                R.id.btn2 -> {
                    viewModel.setStatus("Order Declined", order)
                    viewModel.delete(order)
                }
                R.id.btn3 -> viewModel.setStatus("Order Ready To Collect", order)
                R.id.btn4 -> viewModel.setStatus("Order status : Delivering", order)
                R.id.btn5 -> viewModel.delete(order)
            }
            dialog.hide()
        }


    }


}