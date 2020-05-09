package com.frezzcoding.bolosassuncao.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "user_table")
class User : Serializable {

    @PrimaryKey var id = 0
    lateinit var username: String
    @Ignore lateinit var password : String
    lateinit var email : String
    var privilege = 0;

    @Ignore
    constructor(username : String, password : String){
        this.username = username
        this.password = password
        this.id = id
    }
    @Ignore
    constructor(id : Int, priv : Int){
        this.id = id
        this.privilege = priv
    }
    @Ignore
    constructor(username : String, password: String, email :String){
        this.username = username
        this.password = password
        this.email = email
    }


    constructor() : this(0, 0, "", "")
    //we dont want to store password in room db
    constructor(id : Int, priv : Int, username : String, email :String){
        this.id = id
        this.privilege = priv
        this.username = username
        this.email = email
    }






}


