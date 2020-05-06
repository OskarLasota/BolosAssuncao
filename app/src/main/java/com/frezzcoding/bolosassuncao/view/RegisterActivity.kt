package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.databinding.ActivityRegisterBinding
import com.frezzcoding.bolosassuncao.di.AccountInjection
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.utils.InputValidator
import com.frezzcoding.bolosassuncao.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity(), InputValidator {

    private lateinit var binding : ActivityRegisterBinding
    private var loading : Boolean = false
    private lateinit var viewModel : AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)


        initializeViewModel()
        setListeners()
    }

    companion object{
        const val MIN_PASS_LENGTH = 4
        const val MIN_USERNAME_LENGTH = 4
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

    private val observeRegister = Observer<User>{
        
    }

    private val observeError = Observer<String>{
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

    private fun setListeners(){
        binding.btnRegister.setOnClickListener {
            if(checkInputValidity() && !loading){

            }
        }

        binding.etEditname.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("username")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.etPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkCurrentValidity("password")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
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
        if(binding.etPassword.text.toString() == binding.etRepeatPassword.text.toString()){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}