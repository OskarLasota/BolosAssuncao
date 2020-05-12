package com.frezzcoding.bolosassuncao.view.privileged

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
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.messaging.FirebaseMessaging


class PrivilegedUserActivity : AppCompatActivity() {

    private var loggedin = false
    private lateinit var user : User
    private lateinit var viewModel : CachingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //check if this line below works
        startService(this.intent)
        FirebaseMessaging.getInstance().subscribeToTopic("test")


        if(intent.extras != null){
            user = intent.getSerializableExtra("user") as User
            loggedin = true
        }


        //todo this has to be done in the login activity
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CachingViewModel(application).javaClass)
        viewModel.init()

        setObservers()


    }

    private fun setObservers(){
        //if user just creates an account then make new entry on room db
        viewModel.user.observe(this, Observer {
            if(it == null){
                if(loggedin){
                    viewModel.insert(user)
                }
            }else{
                //if logged in then obtain all data from api for chat and orders
                loggedin = true
            }
            setUI()
        })

        viewModel.loading.observe(this, Observer{
            if(it){
                println("loading")
            }else{
                println("finished loading")
            }
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


    private fun setupBottomNavMenu(navController: NavController) {
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
