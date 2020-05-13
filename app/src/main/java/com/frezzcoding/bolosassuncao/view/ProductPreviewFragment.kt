package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentProductpreviewBinding
import com.frezzcoding.bolosassuncao.models.Product
import com.squareup.picasso.Picasso

class ProductPreviewFragment: Fragment() {

    private lateinit var product : Product
    private lateinit var binding : FragmentProductpreviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_productpreview, container, false
        )

        setProductValues()
        setListeners()
        return binding.root
    }

    private fun setListeners(){
        binding.fabBasket.setOnClickListener {
            //on click we need to run animation if added to the db
            //the basket should only be stored on the room database
            //the order will be stored on the main database
            //how will it be stored on the main database?
            //todo 1. store product on room database when its added user is allowed to add it again
            //todo 2. create the adapter to show all of the items in the basket
        }
    }

    private fun setProductValues(){
        if(arguments!!.get("product") != null) {
            product = arguments!!.get("product") as Product
            binding.product = product
            Picasso.get().load(product.url).into(binding.ivImage)
        }
    }

}