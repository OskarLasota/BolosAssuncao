package com.resocoder.navigationtut


import android.R.attr.bitmap
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import java.io.File
import java.io.IOException


class SettingsFragment : Fragment() {

    private lateinit var _view : View
    private lateinit var viewModel : ProductViewModel
    private lateinit var btnUpload : Button
    //storage permission code
    private val STORAGE_PERMISSION_CODE = 123
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view =  inflater.inflate(R.layout.fragment_settings, container, false)
        initializeView();
        //todo create a com.frezzcoding.bolosassuncao.viewmodel that will connect to the api using retrofit to upload images and obtain images from the server
        //todo this fragment is exclusive for the privilaged users, but com.frezzcoding.bolosassuncao.viewmodel should be reused for normal users and retrieval of images
        //todo the images will be stored in a Room database to allow caching as well as reducing amount of http requests made
        //todo the user should see all the stored pictures and be able to edit the pictures as well as the description and details
        //todo this application is aimed at a bakery company

        //todo design
        //todo design should be a cardview 3x3 or 2x2
        //todo each card should be pressable which will navigate to a new fragment, edit and create fragment can be identical
        //todo on new object create or update, the api as well as the database should be updated.

        initializeViewModel()
        viewModel.getProducts()

        return _view;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this.context, "permission granted", Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initializeView(){
        btnUpload = _view.findViewById(R.id.btn_upload)
        btnUpload.setOnClickListener {
            println("clicked")
            selectImage()
        }
    }

    private fun selectImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null){
            //imageview can be set with .setImageUri(URI);
            //todo call api from here to upload the image into the database
        }
    }

    private fun initializeViewModel(){
        //set com.frezzcoding.bolosassuncao.viewmodel with factory
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(ProductViewModel::class.java)
        //set observers
        viewModel.products.observe(viewLifecycleOwner, renderProducts)

    }

    private val renderProducts = Observer<List<Product>>{
        println("render products success fragment")
        println(it.size)
    }


}
