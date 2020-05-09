package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.models.User
import com.frezzcoding.bolosassuncao.viewmodel.CachingViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import maes.tech.intentanim.CustomIntent

class NeutralUserActivity : AppCompatActivity()  {

    /*
       if user logs in once, their state should save automatically - find out how to get this done
       there should be an animation for around 1-2 seconds while network is used to obtain user data of orders and chat
     */

    /*
        there should be a search bar and maybe a filter system
        the user should be able to press on the product and see a summary of the product
     */


    private var loggedin = false
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
                println("nobody is logged in")
                if(loggedin){
                    viewModel.insert(user)
                }
            }else{
                //if logged in then obtain all data from api for chat and orders
                loggedin = true
            }
            setUI()
        })
    }

    private fun setUI(){
        val navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        setupBottomNavMenu(navController)
        setupSideNavigationMenu(navController)
        setupActionBar(navController)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.destination_settings)?.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }


    private fun setupBottomNavMenu(navController: NavController) {
        if(!loggedin) {
            bottom_nav?.menu?.findItem(R.id.destination_chat)?.isVisible = loggedin
            bottom_nav?.menu?.findItem(R.id.destination_orders)?.isVisible = loggedin
            bottom_nav?.menu?.findItem(R.id.destination_requirelogin1)?.isVisible = !loggedin
            bottom_nav?.menu?.findItem(R.id.destination_requirelogin2)?.isVisible = !loggedin
        }

        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setupSideNavigationMenu(navController: NavController) {
        nav_view?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setupActionBar(navController: NavController) {
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
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

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this,
            R.id.nav_host_fragment
        ),
            drawer_layout)
    }

}