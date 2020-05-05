package com.frezzcoding.bolosassuncao.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
class User {

    @PrimaryKey var id = 0
    lateinit var username: String
    lateinit var password : String
    lateinit var email : String
    var privilege = 0;

    constructor(username : String, password : String){
        this.username = username
        this.password = password
        this.id = id
    }
    constructor(id : Int, priv : Int){
        this.id = id
        this.privilege = priv
    }
    constructor(username : String, password: String, email :String){
        this.username = username
        this.password = password
        this.email = email
    }






}


