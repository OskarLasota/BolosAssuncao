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
import com.frezzcoding.bolosassuncao.databinding.ActivityLoginBinding
import com.frezzcoding.bolosassuncao.di.AccountInjection
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.utils.InputValidator
import com.frezzcoding.bolosassuncao.viewmodel.AccountViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity(), InputValidator {

    private lateinit var viewModel : AccountViewModel
    private lateinit var binding : ActivityLoginBinding
    private var loading : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        when (it.privilege) {
            0 -> login()
            1 -> authorizedLogin()
            else -> Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
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

        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("username")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        binding.etPassword.addTextChangedListener(object : TextWatcher{
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