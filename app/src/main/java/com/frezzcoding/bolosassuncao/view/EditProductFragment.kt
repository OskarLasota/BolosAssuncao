package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentEditproductBinding
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.utils.ProductInputValidator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_editproduct.*

class EditProductFragment : Fragment(), ProductInputValidator {

    private lateinit var _view : View
    private lateinit var image : ImageView
    private lateinit var til_editname : TextInputLayout
    private lateinit var til_editdesc : TextInputLayout
    private lateinit var til_editprice : TextInputLayout
    private lateinit var et_editname : TextInputEditText
    private lateinit var et_editdesc : TextInputEditText
    private lateinit var et_editprice : TextInputEditText

    //binding
    private lateinit var binding : FragmentEditproductBinding
    private lateinit var product : Product



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_editproduct, container, false
        )
        _view = binding.root
        initializeViews()
        setProductValues()
        /*
        pass on product to the fragment
        use the product to initialize all of the views
        connect to view model so user is able to edit or delete the product
        */

        return _view
    }

    private fun setProductValues(){
        println("seting values")
        if(arguments!!.get("product") != null) {
            product = arguments!!.get("product") as Product
            binding.product = product
            //create a method in Product to return an image, from picasso?
            Picasso.get().load(product.url).into(image)

        }
    }

    private fun initializeViews(){
        image = _view.findViewById(R.id.iv_logo)
        til_editname = _view.findViewById(R.id.til_editname)
        til_editdesc = _view.findViewById(R.id.til_editdesc)
        til_editprice = _view.findViewById(R.id.til_editprice)
        et_editname = _view.findViewById(R.id.et_editprice)
        et_editdesc = _view.findViewById(R.id.et_editdesc)
        et_editprice = _view.findViewById(R.id.et_editprice)
    }


    override fun checkCurrentValidity(resource: String) {

    }

    override fun checkInputValidity(): Boolean {



        return true
    }

}