package com.frezzcoding.bolosassuncao.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.di.Injection
import com.frezzcoding.bolosassuncao.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel : ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //need to set up toolbar with back button to previous activity
        setSupportActionBar(toolbar)
        initializeViewModel()

        setListeners()
    }


    private fun setListeners(){
        val btnlogin = findViewById<Button>(R.id.btn_login)
        val btnregister = findViewById<Button>(R.id.btn_nav_register)

        btnlogin.setOnClickListener {
            //call view model here
            //set viewmodel listener to listen for result, if successfull then call login() else show error

        }
        btnregister.setOnClickListener {
            register()
        }
    }

    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(
            ProductViewModel::class.java)
         // viewModel.products.observe(viewLifecycleOwner, renderProducts)
    }

    private fun checkValidity() : Boolean{
        var result = false


        return result
    }

    private fun login() = startActivity(Intent(this, PrivilegedUserActivity::class.java))

    private fun register() = startActivity(Intent(this, RegisterActivity::class.java))



}