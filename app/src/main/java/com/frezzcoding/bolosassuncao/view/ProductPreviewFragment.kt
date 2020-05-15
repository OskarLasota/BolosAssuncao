package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentProductpreviewBinding
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.view.neutral.NeutralUserActivity
import com.frezzcoding.bolosassuncao.view.privileged.PrivilegedUserActivity
import com.frezzcoding.bolosassuncao.viewmodel.BasketViewModel
import com.squareup.picasso.Picasso

class ProductPreviewFragment: Fragment() {

    private lateinit var product : Product
    private lateinit var binding : FragmentProductpreviewBinding
    private lateinit var basketViewModel : BasketViewModel
    private var inProgress : Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_productpreview, container, false
        )

        basketViewModel = ViewModelProvider.AndroidViewModelFactory(activity!!.application).create(BasketViewModel(activity!!.application).javaClass)
        basketViewModel.init()


        setProductValues()
        setListeners()
        return binding.root
    }

    private fun setListeners(){
        //User is only to add again after first call is finished
        basketViewModel.loading.observe(viewLifecycleOwner, Observer{
            inProgress = it
        })

        if(activity is PrivilegedUserActivity){
            binding.fabBasket.hide()
        }else{
            binding.fabBasket.setOnClickListener {
                if ((activity as NeutralUserActivity).loggedin) {
                    if (!inProgress) {
                        if (arguments!!.get("product") != null) {
                            basketViewModel.insert(arguments!!.get("product") as Product)
                        }
                    }
                } else {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.destination_requirelogin1)
                }
            }
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