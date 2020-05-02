package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.frezzcoding.bolosassuncao.R

class RequireLoginFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var _view =  inflater.inflate(R.layout.fragment_loginrequired, container, false)

        var login = _view.findViewById<Button>(R.id.btn_login_request)

        login.setOnClickListener {
            Navigation.findNavController(_view).navigate(R.id.destination_login)
        }

        return _view
    }

}