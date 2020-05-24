package com.frezzcoding.bolosassuncao.view.privileged

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
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentAddproductBinding
import com.frezzcoding.bolosassuncao.di.ProductInjection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.utils.InputValidator
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Double.parseDouble


class AddProductFragment : Fragment() , InputValidator {


    private lateinit var viewModel : ProductViewModel
    private lateinit var binding : FragmentAddproductBinding

    companion object{
        const val minNameLength = 4;
        var encode = ""
        const val STORAGE_PERMISSION_CODE = 123
        const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddproductBinding.inflate(inflater)

        initializeListeners()
        initializeViewModel()

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            val imageUri = data.data
            val imageStream: InputStream? = requireContext().contentResolver.openInputStream(imageUri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            encode = encodeImage(selectedImage)
            binding.ivStatus.setImageDrawable(resources.getDrawable(R.drawable.ic_done_black_24dp, null))
        }
    }

    private fun submitForm(){
        if(checkInputValidity()) {
            var product = Product(0, "", binding.etName.text.toString(), binding.etDescription.text.toString(),
                encode, parseDouble(binding.etPrice.text.toString()))
            viewModel.upload(product)
        }
    }

    private fun selectImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_REQUEST
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this.context, "permission granted", Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private val checkResult = Observer<Boolean>{
        if(it){
            Navigation.findNavController(binding.root).navigate(R.id.destination_edit)
        }else{
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }



    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, ProductInjection.provideViewModelFactory()).get(ProductViewModel::class.java)
        viewModel.upload.observe(viewLifecycleOwner, checkResult)
    }


    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


    private fun initializeListeners(){
        binding.btnSubmit.setOnClickListener{
            submitForm()
        }
        binding.ivSelectimage.setOnClickListener{
            selectImage()
        }

        binding.etName.doAfterTextChanged {
            checkCurrentValidity("name")
        }
        binding.etPrice.doAfterTextChanged {
            checkCurrentValidity("price")
        }

    }

    override fun checkCurrentValidity(resource : String){
        when(resource){
            "name" -> if(binding.etName.text.toString().length >= minNameLength) {binding.tilName.error = null; }
            "price" -> if(binding.etPrice.text.toString().isNotEmpty()) {binding.tilPrice.error = null;}
        }
    }


    override fun checkInputValidity() : Boolean {
        if (binding.etName.text.toString().length < minNameLength) {
            binding.tilName.error = getString(R.string.product_name_error)
            return false
        }
        if (binding.etPrice.text.toString().isEmpty()) {
            binding.tilPrice.error = getString(R.string.product_price_error)
            return false
        }
        if(encode.isEmpty()){
            return false
        }
        return true
    }


}