package com.frezzcoding.bolosassuncao.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.view.neutral.NeutralUserActivity
import com.frezzcoding.bolosassuncao.view.privileged.PrivilegedUserActivity
import com.frezzcoding.bolosassuncao.viewmodel.CachingViewModel
import com.google.firebase.messaging.FirebaseMessaging
import maes.tech.intentanim.CustomIntent


class FirstActivity : AppCompatActivity() {

    private lateinit var viewModel : CachingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService(this.intent)
        FirebaseMessaging.getInstance().subscribeToTopic("test")

        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CachingViewModel(application).javaClass)
        viewModel.init()
        setObservers()

    }

    private fun setObservers(){
        //if user just creates an account then make new entry on room db
        viewModel.getUser().observe(this, Observer {
            if(it == null){
                intent = Intent(this, NeutralUserActivity::class.java)
                startActivity(intent)
                CustomIntent.customType(this, "fadein-to-fadeout")
            }else{
                //if logged in then obtain all data from api for chat and orders

                //this redirection needs an alternative
                if(it.privilege == 1){
                    intent = Intent(this, PrivilegedUserActivity::class.java)
                    intent.putExtra("user", it)
                    startActivity(intent)
                    CustomIntent.customType(this, "fadein-to-fadeout")
                }
            }
        })
    }

}