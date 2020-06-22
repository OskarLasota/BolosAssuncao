package com.frezzcoding.bolosassuncao.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.frezzcoding.bolosassuncao.databinding.ActivityFirstBinding
import com.frezzcoding.bolosassuncao.di.AccountInjection
import com.frezzcoding.bolosassuncao.view.neutral.NeutralUserActivity
import com.frezzcoding.bolosassuncao.view.privileged.PrivilegedUserActivity
import com.frezzcoding.bolosassuncao.viewmodel.AccountViewModel
import com.frezzcoding.bolosassuncao.viewmodel.CachingViewModel
import com.frezzcoding.bolosassuncao.viewmodel.ViewModelFactory
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import maes.tech.intentanim.CustomIntent


class FirstActivity : AppCompatActivity() {

    private lateinit var viewModel : CachingViewModel
    private lateinit var userViewModel : AccountViewModel
    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CachingViewModel(application).javaClass)
        viewModel.init()
        userViewModel = ViewModelProvider(this, AccountInjection.provideViewModelFactory()).get(
            AccountViewModel::class.java)

        setObservers()
        binding.animation?.visibility = View.VISIBLE
        binding.animation?.playAnimation()
    }


    private fun setObservers(){
        userViewModel.isViewLoading.observe(this, Observer{
            if(!it){
                startActivity(intent)
                CustomIntent.customType(this, "fadein-to-fadeout")
            }else{
                startActivity(intent)
                CustomIntent.customType(this, "fadein-to-fadeout")
            }
        })

        viewModel.user.observe(this, Observer {
            binding.animation?.visibility = View.GONE
            intent = Intent(this, NeutralUserActivity::class.java)
            if(it == null){
                startActivity(intent)
                CustomIntent.customType(this, "fadein-to-fadeout")
            }else{
                if(it.privilege == 1 || it.privilege == 2) {
                    intent = Intent(this, PrivilegedUserActivity::class.java)
                }
                intent.putExtra("user", it)
                startService(this.intent)
                FirebaseMessaging.getInstance().subscribeToTopic("test")
                FirebaseInstanceId.getInstance().token?.let { it1 -> userViewModel.updateToken(it1, it.id) }

            }
        })
    }

}