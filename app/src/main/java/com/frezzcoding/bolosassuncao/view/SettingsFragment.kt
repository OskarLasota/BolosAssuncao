package com.resocoder.navigationtut


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.di.Injection
import com.frezzcoding.bolosassuncao.models.Product
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel


class SettingsFragment : Fragment() {

    private lateinit var viewer : View
    private lateinit var viewModel : ProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewer =  inflater.inflate(R.layout.fragment_settings, container, false)

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

        return viewer;
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
