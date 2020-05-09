package com.frezzcoding.bolosassuncao.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "user_table")
class User : Serializable {

    @PrimaryKey var id = 0
    @Ignore lateinit var username: String
    @Ignore lateinit var password : String
    @Ignore lateinit var email : String
    var privilege = 0;

    @Ignore
    constructor(username : String, password : String){
        this.username = username
        this.password = password
        this.id = id
    }

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

    //used to make room db work not sure if this is correct
    constructor() : this(0, 0)







}


