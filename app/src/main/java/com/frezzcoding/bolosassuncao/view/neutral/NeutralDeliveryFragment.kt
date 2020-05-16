package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentDeliveryBinding
import com.frezzcoding.bolosassuncao.utils.InputValidator

class NeutralDeliveryFragment : Fragment(), InputValidator {

    private lateinit var binding : FragmentDeliveryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_delivery, container, false
        )
        if(activity is NeutralUserActivity){
            (activity as NeutralUserActivity)?.hideBottombar(true)
        }

        //call api to create an order in the main database ( not local database ) & remove all basket items

        return binding.root
    }

    override fun checkCurrentValidity(resource: String) {
        TODO("Not yet implemented")
    }

    override fun checkInputValidity(): Boolean {
        TODO("Not yet implemented")
    }
}