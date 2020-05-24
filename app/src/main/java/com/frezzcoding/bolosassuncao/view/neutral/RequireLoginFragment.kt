package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.frezzcoding.bolosassuncao.R
import maes.tech.intentanim.CustomIntent

class RequireLoginFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_loginrequired, container, false)
        var login = view.findViewById<Button>(R.id.btn_login_request)

        login.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.destination_login)
            CustomIntent.customType(this.context, "fadein-to-fadeout")
        }

        return view
    }



}