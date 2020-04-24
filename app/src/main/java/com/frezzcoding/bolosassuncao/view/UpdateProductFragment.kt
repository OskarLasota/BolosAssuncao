package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.frezzcoding.bolosassuncao.R
import com.google.android.material.textfield.TextInputEditText


class UpdateProductFragment : Fragment(){

    private lateinit var _view : View
    private lateinit var etName : TextInputEditText
    private lateinit var etDescription : TextInputEditText
    private lateinit var etPrice : TextInputEditText
    private lateinit var ivStatus : ImageView
    private lateinit var ivSelect : ImageView
    private lateinit var btnSubmit : Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view =  inflater.inflate(R.layout.fragment_updateproduct, container, false)
        initializeView()
        initializeListeners()
        return _view
    }

    private fun initializeView(){
        etName = _view.findViewById(R.id.et_name)
        etDescription = _view.findViewById(R.id.et_description)
        etPrice = _view.findViewById(R.id.et_price)
        ivStatus = _view.findViewById(R.id.iv_status)
        ivSelect = _view.findViewById(R.id.iv_selectimage)
        btnSubmit = _view.findViewById(R.id.btn_submit)
    }

    private fun initializeListeners(){
        btnSubmit.setOnClickListener{
            submitForm()
        }
        ivSelect.setOnClickListener{
            selectImage()
        }
    }

    private fun submitForm(){

    }

    private fun selectImage(){
        //ivStatus update to green tick if correct
    }

}