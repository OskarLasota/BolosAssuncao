package com.frezzcoding.bolosassuncao.view

import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.di.AccountInjection
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.utils.InputValidator
import com.frezzcoding.bolosassuncao.viewmodel.AccountViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity(), InputValidator {

    private lateinit var viewModel : AccountViewModel
    private lateinit var etUsername : TextInputEditText
    private lateinit var etPassword : TextInputEditText
    private lateinit var tilUsername : TextInputLayout
    private lateinit var tilPassword : TextInputLayout
    private lateinit var btnlogin : Button
    private lateinit var btnregister : Button
    private var loadingAnimation: LottieAnimationView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //need to set up toolbar with back button to previous activity
        initializeViews()
        setSupportActionBar(toolbar)
        initializeViewModel()

        setListeners()
    }

    companion object{
        const val MIN_PASS_LENGTH = 4
        const val MIN_USERNAME_LENGTH = 4
    }

    private fun login() = startActivity(Intent(this, NeutralUserActivity::class.java))

    private fun authorizedLogin() = startActivity(Intent(this, PrivilegedUserActivity::class.java))

    private fun register() = startActivity(Intent(this, RegisterActivity::class.java))


    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this, AccountInjection.provideViewModelFactory()).get(
           AccountViewModel::class.java)
        viewModel.user.observe(this, observeLogin)
        viewModel.onMessageError.observe(this, observeError)
        viewModel.isViewLoading.observe(this, observeLoading)
    }

    private val observeLoading = Observer<Boolean>{
        //animations
        if(it){
            loadingAnimation?.visibility = View.VISIBLE
            loadingAnimation?.playAnimation()
        }else{
            loadingAnimation?.visibility = View.GONE
        }
    }

    private val observeError = Observer<String>{
        //types of errors
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

    private val observeLogin = Observer<User>{
        //on success call login

        when (it.privilege) {
            0 -> login()
            1 -> authorizedLogin()
            else -> Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }


    override fun checkCurrentValidity(resource: String) {
        when(resource){
            "username" -> if(etUsername.text.toString().length > MIN_USERNAME_LENGTH) {tilUsername.error = null; }
            "password" -> if(etPassword.text.toString().length > MIN_PASS_LENGTH) {tilPassword.error = null;}
        }
    }

    override fun checkInputValidity(): Boolean {
        if (etUsername.text.toString().length < MIN_USERNAME_LENGTH) {
            tilUsername.error = "Username too short"
            return false
        }
        if (etPassword.text.toString().length < MIN_PASS_LENGTH) {
            tilPassword.error = "Password too short"
            return false
        }
        return true
    }

    private fun initializeViews(){
        tilUsername = findViewById(R.id.til_username)
        tilPassword = findViewById(R.id.til_password)
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnlogin = findViewById(R.id.btn_login)
        btnregister = findViewById(R.id.btn_nav_register)
        loadingAnimation = findViewById(R.id.animation)
    }


    private fun setListeners(){
        btnlogin.setOnClickListener {
            if(checkInputValidity()) {
                var user = User(etUsername.text.toString(), etPassword.text.toString())
                viewModel.getUser(user)
            }
        }
        btnregister.setOnClickListener {
            register()
        }

        etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("username")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        etPassword.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("password")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


}