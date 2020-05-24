package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.frezzcoding.bolosassuncao.R
import maes.tech.intentanim.CustomIntent

class OrderSuccessFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_ordersuccess, container, false)
        if(activity is NeutralUserActivity){
            (activity as NeutralUserActivity)?.hideBottombar(false)
            (activity as NeutralUserActivity)?.hideToolbar(true)
        }

        return view
    }

    override fun onDestroy() {
        (activity as NeutralUserActivity)?.hideToolbar(false)
        super.onDestroy()
    }





}