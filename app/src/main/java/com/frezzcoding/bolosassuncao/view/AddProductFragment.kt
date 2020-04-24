package com.frezzcoding.bolosassuncao.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.di.Injection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class AddProductFragment : Fragment(){

    private lateinit var _view : View
    private lateinit var etName : TextInputEditText
    private lateinit var etDescription : TextInputEditText
    private lateinit var etPrice : TextInputEditText
    private lateinit var ivStatus : ImageView
    private lateinit var ivSelect : ImageView
    private lateinit var btnSubmit : Button
    private lateinit var encode : String
    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123
    private val PICK_IMAGE_REQUEST = 1
    //viewmodel
    private lateinit var viewModel : ProductViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view =  inflater.inflate(R.layout.fragment_updateproduct, container, false)
        initializeView()
        initializeListeners()
        initializeViewModel()
        return _view
    }

    private fun initializeViewModel(){
        //set com.frezzcoding.bolosassuncao.viewmodel with factory
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(ProductViewModel::class.java)
        //set observers
        viewModel.upload.observe(viewLifecycleOwner, checkResult)
    }

    private val checkResult = Observer<Boolean>{
        //on success
        if(it){
            println("success here")
        }
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
        //loading animation then go back 1 fragment and update the list
        //call viewmodel here
        var product = Product(1, "fasdfasdf", "Tapioca", "very tasty", encode, 8.50)
        viewModel.upload(product)
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
            //data.data is uri
            //todo call api from here to upload the image into the database
            //todo ivStatus update to green tick if correct
            //need to find real path i think
            val imageUri = data.data
            val imageStream: InputStream? = requireContext().getContentResolver().openInputStream(imageUri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            encode = encodeImage(selectedImage)
            //make a retrofit POST request with this image and see if it works

        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }



}