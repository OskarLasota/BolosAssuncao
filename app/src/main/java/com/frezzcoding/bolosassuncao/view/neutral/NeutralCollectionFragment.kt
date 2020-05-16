package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentCollectionBinding
import com.frezzcoding.bolosassuncao.utils.InputValidator

class NeutralCollectionFragment : Fragment(), InputValidator {

    private lateinit var binding : FragmentCollectionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_collection, container, false
        )
        if(activity is NeutralUserActivity){
            (activity as NeutralUserActivity)?.hideBottombar(true)
        }
        return binding.root

    }

    override fun checkCurrentValidity(resource: String) {
        TODO("Not yet implemented")
    }

    override fun checkInputValidity(): Boolean {
        TODO("Not yet implemented")
    }

}