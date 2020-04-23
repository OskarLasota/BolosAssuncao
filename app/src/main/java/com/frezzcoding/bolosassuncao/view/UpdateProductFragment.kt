package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R


class UpdateProductFragment : Fragment(){

    private lateinit var _view : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view =  inflater.inflate(R.layout.fragment_updateproducts, container, false)


        return _view
    }


}