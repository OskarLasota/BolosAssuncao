package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.frezzcoding.bolosassuncao.R
import com.frezzcoding.bolosassuncao.models.User
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class NeutralUserActivity : AppCompatActivity()  {

    /*
       user does not  have to log in to explore the products
       user will be taken to the login screen when they want to make an order or speak to the privilaged user
       if user logs in once, their state should save automatically - find out how to get this done
       there should be an animation for around 1-2 seconds while room database is used to obtain user data
     */

    /*
        todo
        /this activity should assign the user token
        /this activity should allow user to view all the products
        there should be a search bar and maybe a filter system
        the user should be able to press on the product and see a summary of the product
        the user should have an option to log in and register
     */


    private var loggedin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neutral)
        setSupportActionBar(toolbar)

        val navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        if(intent.extras != null){
            var user = intent.getSerializableExtra("user") as? User
        }
        
        //these should be set up respecting if the user is logged in or not
        setupBottomNavMenu(navController)
        setupSideNavigationMenu(navController)
        setupActionBar(navController)

        startService(this.intent)
        FirebaseMessaging.getInstance().subscribeToTopic("test")

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