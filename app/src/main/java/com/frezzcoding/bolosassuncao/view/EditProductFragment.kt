package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EditProductFragment : Fragment() {

    private lateinit var _view : View
    private lateinit var image : ImageView
    private lateinit var til_editname : TextInputLayout
    private lateinit var til_editdesc : TextInputLayout
    private lateinit var til_editprice : TextInputLayout
    private lateinit var et_editname : TextInputEditText
    private lateinit var et_editdesc : TextInputEditText
    private lateinit var et_editprice : TextInputEditText



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view =  inflater.inflate(R.layout.fragment_editproduct, container, false)
        initializeViews()
        /*
        pass on product to the fragment
        use the product to initialize all of the views
        connect to viewmodel so user is able to edit or delete the product
         */


        return _view
    }



    private fun initializeViews(){
        til_editname = _view.findViewById(R.id.til_editname)
        til_editdesc = _view.findViewById(R.id.til_editdesc)
        til_editprice = _view.findViewById(R.id.til_editprice)
        et_editname = _view.findViewById(R.id.et_editprice)
        et_editdesc = _view.findViewById(R.id.et_editdesc)
    }

}