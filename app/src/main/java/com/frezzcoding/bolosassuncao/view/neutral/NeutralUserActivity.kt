package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.viewmodel.CachingViewModel
import kotlinx.android.synthetic.main.activity_neutral.*

class NeutralUserActivity : AppCompatActivity()  {


    var loggedin = false
    private lateinit var user : User
    private lateinit var viewModel : CachingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neutral)
        setSupportActionBar(toolbar)

        if(intent.extras != null){
            user = intent.getSerializableExtra("user") as User
            loggedin = true
        }

        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CachingViewModel(application).javaClass)
        viewModel.init()

        setObservers()
        setUI()
    }

    fun hideToolbar(isHidden: Boolean){
        if(isHidden){
            supportActionBar?.hide()
        }else{
            supportActionBar?.show()
        }

    }

    fun hideBottombar(isHidden : Boolean){
        if(isHidden)
            bottom_nav?.visibility = View.GONE
        else
            bottom_nav?.visibility = View.VISIBLE
    }

    private fun setObservers(){
        viewModel.user.observe(this, Observer {

        })
    }

    private fun setUI(){
        val navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        setupBottomNavMenu(navController)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.destination_edit)?.isVisible = false
        if(!loggedin){
            menu?.findItem(R.id.destination_basket)?.isVisible = false
        }else{
            menu?.findItem(R.id.destination_login)?.isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }



    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav?.menu?.findItem(R.id.destination_timetable)?.isVisible = false
        bottom_nav?.menu?.findItem(R.id.destination_priv_orders)?.isVisible = false
        bottom_nav?.menu?.findItem(R.id.destination_chat)?.isEnabled = loggedin
        bottom_nav?.menu?.findItem(R.id.destination_orders)?.isEnabled = loggedin


        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        val navigated = NavigationUI.onNavDestinationSelected(item!!, navController)
        return navigated || super.onOptionsItemSelected(item)
    }


}