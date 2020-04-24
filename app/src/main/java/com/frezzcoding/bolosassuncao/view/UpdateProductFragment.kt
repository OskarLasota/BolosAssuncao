package com.frezzcoding.bolosassuncao.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
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
    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123
    private val PICK_IMAGE_REQUEST = 1


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
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this.context, "permission granted", Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            //imageview can be set with .setImageUri(URI);
            //data.data = uri
            //todo call api from here to upload the image into the database
            //todo ivStatus update to green tick if correct
        }
    }


}