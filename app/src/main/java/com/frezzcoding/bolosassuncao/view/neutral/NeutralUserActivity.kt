package com.frezzcoding.bolosassuncao.view.neutral

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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



    private fun setObservers(){
        viewModel.user.observe(this, Observer {

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