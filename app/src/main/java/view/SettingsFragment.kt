package com.resocoder.navigationtut


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.R
import viewmodel.ProductViewModel


class SettingsFragment : Fragment() {

    private lateinit var viewer : View
    private lateinit var productViewModel : ProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewer =  inflater.inflate(R.layout.fragment_settings, container, false)

        //todo create a viewmodel that will connect to the api using retrofit to upload images and obtain images from the server
        //todo this fragment is exclusive for the privilaged users, but viewmodel should be reused for normal users and retrieval of images
        //todo the images will be stored in a Room database to allow caching as well as reducing amount of http requests made
        //todo the user should see all the stored pictures and be able to edit the pictures as well as the description and details
        //todo this application is aimed at a bakery company

        //todo design
        //todo design should be a cardview 3x3 or 2x2
        //todo each card should be pressable which will navigate to a new fragment, edit and create fragment can be identical
        //todo on new object create or update, the api as well as the database should be updated.

        return viewer;
    }



}
