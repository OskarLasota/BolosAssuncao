package com.frezzcoding.bolosassuncao.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.navigation.Navigation
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.di.Injection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.utils.ProductInputValidator
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_updateproduct.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.lang.Double.parseDouble
import java.util.regex.Pattern


class AddProductFragment : Fragment() , ProductInputValidator {

    private lateinit var _view : View
    private lateinit var etName : TextInputEditText
    private lateinit var etDescription : TextInputEditText
    private lateinit var etPrice : TextInputEditText
    private lateinit var tilName : TextInputLayout
    private lateinit var tilPrice : TextInputLayout
    private lateinit var ivStatus : ImageView
    private lateinit var ivSelect : ImageView
    private lateinit var btnSubmit : Button
    private lateinit var viewModel : ProductViewModel

    companion object{
        const val minNameLength = 4;
        var encode = ""
        const val STORAGE_PERMISSION_CODE = 123
        const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view =  inflater.inflate(R.layout.fragment_updateproduct, container, false)
        initializeView()
        initializeListeners()
        initializeViewModel()
        println("on create view")
        return _view
    }

    private fun initializeViewModel(){
        //set viewmodel with factory
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(ProductViewModel::class.java)
        //set observers
        viewModel.upload.observe(viewLifecycleOwner, checkResult)
    }

    private val checkResult = Observer<Boolean>{
        if(it){
            Navigation.findNavController(_view).navigate(R.id.destination_settings)
        }else{
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }


    override fun checkCurrentValidity(resource : String){
        when(resource){
            "name" -> if(etName.text.toString().length >= minNameLength) {tilName.error = null; }
            "price" -> if(etPrice.text.toString().isNotEmpty()) {tilPrice.error = null;}
        }
    }

    override fun checkInputValidity() : Boolean {
        if (etName.text.toString().length < minNameLength) {
            tilName.error = getString(R.string.product_name_error)
            return false
        }
        if (etPrice.text.toString().isEmpty()) {
            tilPrice.error = getString(R.string.product_price_error)
            return false
        }
        if(encode.isEmpty()){
            return false
        }
        return true
    }

    private fun submitForm(){
        if(checkInputValidity()) {
            var product = Product(0, "", etName.text.toString(), etDescription.text.toString(), encode, parseDouble(etPrice.text.toString()))
            viewModel.upload(product)
        }
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
            val imageUri = data.data
            val imageStream: InputStream? = requireContext().contentResolver.openInputStream(imageUri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            encode = encodeImage(selectedImage)
            ivStatus.setImageDrawable(resources.getDrawable(R.drawable.ic_done_black_24dp, null))
        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


    private fun initializeView(){
        etName = _view.findViewById(R.id.et_name)
        etDescription = _view.findViewById(R.id.et_description)
        etPrice = _view.findViewById(R.id.et_price)
        tilName = _view.findViewById(R.id.til_name)
        tilPrice = _view.findViewById(R.id.til_price)
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

        etName.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("name")
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        etPrice.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("price")
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }


}