package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.viewmodel.BasketViewModel
import com.frezzcoding.bolosassuncao.viewmodel.CachingViewModel

class OrderSuccessFragment : Fragment() {

    private lateinit var basketViewModel : BasketViewModel

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

        basketViewModel = ViewModelProvider.AndroidViewModelFactory(activity!!.application).create(BasketViewModel(activity!!.application).javaClass)
        basketViewModel.init()
        basketViewModel.deleteAll()

        return view
    }

    override fun onDestroy() {
        (activity as NeutralUserActivity)?.hideToolbar(false)
        super.onDestroy()
    }





}