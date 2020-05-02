package com.frezzcoding.bolosassuncao.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.frezzcoding.bolosassuncao.R
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //need to set up toolbar with back button to previous activity
        setSupportActionBar(toolbar)


        setListeners()
    }


    private fun setListeners(){
        val btnlogin = findViewById<Button>(R.id.btn_login)
        val btnregister = findViewById<Button>(R.id.btn_nav_register)

        btnlogin.setOnClickListener {
            login()
        }
        btnregister.setOnClickListener {
            register()
        }


    }

    private fun login(){
        var intent = Intent(this, PrivilegedUserActivity::class.java)
        startActivity(intent)
    }
    private fun register(){
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }


}