package com.frezzcoding.bolosassuncao.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.databinding.ActivityRegisterBinding
import com.frezzcoding.bolosassuncao.di.AccountInjection
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.utils.InputValidator
import com.frezzcoding.bolosassuncao.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.activity_main.*
import maes.tech.intentanim.CustomIntent

class RegisterActivity : AppCompatActivity(), InputValidator {

    private lateinit var binding : ActivityRegisterBinding
    private var loading : Boolean = false
    private lateinit var viewModel : AccountViewModel
    private val MIN_PASS_LENGTH = 4
    private val MIN_USERNAME_LENGTH = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)


        initializeViewModel()
        setListeners()
    }


    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, AccountInjection.provideViewModelFactory()).get(
            AccountViewModel::class.java)
        viewModel.user.observe(this, observeRegister)
        viewModel.onMessageError.observe(this, observeError)
        viewModel.isViewLoading.observe(this, observeLoading)
    }

    private val observeLoading = Observer<Boolean>{
        if(it){
            loading = true
            binding.animation?.visibility = View.VISIBLE
            binding.animation?.playAnimation()
        }else{
            loading = false
            binding.animation?.visibility = View.GONE
        }
    }

    private fun onRegisterSuccess() {
        startActivity(Intent(this, LoginActivity::class.java))
        CustomIntent.customType(this, "fadein-to-fadeout")
    }

    private val observeRegister = Observer<User>{
        onRegisterSuccess()
    }

    private val observeError = Observer<String>{ Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }

    private fun setListeners(){
        binding.btnRegister.setOnClickListener {
            if(checkInputValidity() && !loading){
                var user = User(binding.etEditname.text.toString(), binding.etPassword.text.toString(), binding.etEmail.text.toString())
                viewModel.registerUser(user)
            }
        }
        binding.etEditname.doAfterTextChanged {
            checkCurrentValidity("username")
        }
        binding.etPassword.doAfterTextChanged {
            checkCurrentValidity("password")
        }
    }


    override fun checkCurrentValidity(resource: String) {
        when(resource){
            "username" -> if(binding.etEditname.text.toString().length > MIN_USERNAME_LENGTH) {binding.tilUsername.error = null; }
            "password" -> if(binding.etPassword.text.toString().length > MIN_PASS_LENGTH) {binding.tilPassword.error = null;}
        }
    }

    override fun checkInputValidity(): Boolean {
        if (binding.etEditname.text.toString().length < MIN_USERNAME_LENGTH) {
            binding.tilUsername.error = "Username too short"
            return false
        }
        if (binding.etPassword.text.toString().length < MIN_PASS_LENGTH) {
            binding.tilPassword.error = "Password too short"
            return false
        }
        if(binding.etPassword.text.toString() != binding.etRepeatPassword.text.toString()){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}