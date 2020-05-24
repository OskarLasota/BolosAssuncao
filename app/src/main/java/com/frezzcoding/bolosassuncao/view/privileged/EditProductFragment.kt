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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.FragmentEditproductBinding
import com.frezzcoding.bolosassuncao.di.ProductInjection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.utils.InputValidator
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.InputStream

class EditProductFragment : Fragment(), InputValidator {


    private lateinit var binding : FragmentEditproductBinding
    private lateinit var product : Product
    private lateinit var viewModel : ProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_editproduct, container, false
        )
        initializeViewModel()
        setProductValues()
        setListeners()
        setObservers()

        return binding.root
    }

    companion object{
        const val minNameLength = 4;
        var encode = ""
        const val STORAGE_PERMISSION_CODE = 123
        const val PICK_IMAGE_REQUEST = 1
        var imageUpdated = false
    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, ProductInjection.provideViewModelFactory()).get(ProductViewModel::class.java)
    }

    private fun setObservers(){
        viewModel.upload.observe(viewLifecycleOwner, checkResult)
        viewModel.isViewLoading.observe(viewLifecycleOwner, checkLoading)
    }

    private val checkLoading = Observer<Boolean>{
        if(it){
            println("loading is true")
        }else{
            println("loading is false - not loading")
        }
    }

    private val checkResult = Observer<Boolean>{
        if(it){
            Navigation.findNavController(binding.root).navigate(R.id.destination_edit)
        }else{
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListeners(){
        binding.btnUpdate.setOnClickListener {
            submitForm()
        }

        binding.ivLogo.setOnClickListener{
            selectImage()
        }

        binding.etEditprice.doAfterTextChanged {
            checkCurrentValidity("price")
        }
        binding.etEditname.doAfterTextChanged {
            checkCurrentValidity("name")
        }

    }

    private fun submitForm(){
        if(checkInputValidity()) {
            product.price = binding.etEditprice.text.toString().toDouble()
            product.description = binding.etEditdesc.text.toString()
            if(imageUpdated) {
                product.encode = encode
            }
            product.name = binding.etEditname.text.toString()

            viewModel.update(product)
        }
    }

    private fun setProductValues(){
        if(arguments!!.get("product") != null) {
            product = arguments!!.get("product") as Product
            binding.product = product
            Picasso.get().load(product.url).into(binding.ivLogo)
        }
    }


    override fun checkCurrentValidity(resource: String) {
        when(resource){
            "name" -> if(binding.etEditname.text.toString().length >= minNameLength) {binding.tilEditname.error = null; }
            "price" -> if(binding.etEditprice.text.toString().isNotEmpty()) {binding.tilEditprice.error = null;}
        }
    }

    override fun checkInputValidity(): Boolean {
        if (binding.etEditname.text.toString().length < minNameLength) {
            binding.tilEditname.error = getString(R.string.product_name_error)
            return false
        }
        if (binding.etEditprice.text.toString().isEmpty()) {
            binding.tilEditprice.error = getString(R.string.product_price_error)
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this.context, "permission granted", Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun encodeImage(bm: Bitmap): String {
        imageUpdated = true
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            val imageUri = data.data
            val imageStream: InputStream? = requireContext().contentResolver.openInputStream(imageUri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            encode = encodeImage(selectedImage)
            binding.ivLogo.setImageBitmap(selectedImage)
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

}