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
import com.frezzcoding.bolosassuncao.databinding.ActivityLoginBinding
import com.frezzcoding.bolosassuncao.di.AccountInjection
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.utils.InputValidator
import com.frezzcoding.bolosassuncao.view.neutral.NeutralUserActivity
import com.frezzcoding.bolosassuncao.view.privileged.PrivilegedUserActivity
import com.frezzcoding.bolosassuncao.viewmodel.AccountViewModel
import com.frezzcoding.bolosassuncao.viewmodel.CachingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import maes.tech.intentanim.CustomIntent

class LoginActivity : AppCompatActivity(), InputValidator {

    private lateinit var viewModel: AccountViewModel
    private lateinit var cachingViewModel : CachingViewModel
    private lateinit var binding: ActivityLoginBinding
    private var loading: Boolean = false
    private lateinit var _user : User
    private val MIN_PASS_LENGTH = 4
    private val MIN_USERNAME_LENGTH = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)
        initializeViewModel()

        setListeners()
    }


    private fun login(user: User) {
        var intent =
            if(user.privilege == 0)
                Intent(this, NeutralUserActivity::class.java)
            else
                Intent(this, PrivilegedUserActivity::class.java)
        
        intent.putExtra("user", user)
        startActivity(intent)
        CustomIntent.customType(this, "fadein-to-fadeout")
    }

    private fun register() {
        startActivity(Intent(this, RegisterActivity::class.java))
        CustomIntent.customType(this, "fadein-to-fadeout")
    }


    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, AccountInjection.provideViewModelFactory()).get(
           AccountViewModel::class.java)
        viewModel.user.observe(this, observeLogin)
        viewModel.onMessageError.observe(this, observeError)
        viewModel.isViewLoading.observe(this, observeLoading)

        cachingViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CachingViewModel(application).javaClass)
        cachingViewModel.init()
        cachingViewModel.loading.observe(this, observeCachingLoading)

    }

    private val observeCachingLoading = Observer<Boolean>{
        //when process finishes loading
        if(!it){
            login(_user)
        }
    }

    private val observeLoading = Observer<Boolean>{
        //animations
        if(it){
            loading = true
            binding.animation?.visibility = View.VISIBLE
            binding.animation?.playAnimation()
        }else{
            loading = false
            binding.animation?.visibility = View.GONE
        }
    }

    private val observeError = Observer<String>{
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

    private val observeLogin = Observer<User>{
        _user = it
        cachingViewModel.insert(it)
    }


    override fun checkCurrentValidity(resource: String) {
        when(resource){
            "username" -> if(binding.etUsername.text.toString().length > MIN_USERNAME_LENGTH) {binding.tilUsername.error = null; }
            "password" -> if(binding.etPassword.text.toString().length > MIN_PASS_LENGTH) {binding.tilPassword.error = null;}
        }
    }

    override fun checkInputValidity(): Boolean {
        if (binding.etUsername.text.toString().length < MIN_USERNAME_LENGTH) {
            binding.tilUsername.error = "Username too short"
            return false
        }
        if (binding.etPassword.text.toString().length < MIN_PASS_LENGTH) {
            binding.tilPassword.error = "Password too short"
            return false
        }
        return true
    }

    private fun setListeners(){
        binding.btnLogin.setOnClickListener {
            if(checkInputValidity() && !loading) {
                var user = User(binding.etUsername.text.toString(), binding.etPassword.text.toString())
                viewModel.getUser(user)
            }
        }
        binding.btnNavRegister.setOnClickListener {
            if(!loading) {
                register()
            }
        }
        binding.etUsername.doAfterTextChanged {
            checkCurrentValidity("username")
        }
        binding.etPassword.doAfterTextChanged {
            checkCurrentValidity("password")
        }
    }


}