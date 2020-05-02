package com.frezzcoding.bolosassuncao.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frezzcoding.bolosassuncao.R
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(toolbar)

    }

}