package com.frezzcoding.bolosassuncao.models

class User {

    private var id = 0
    private lateinit var username: String
    private lateinit var password : String

    constructor(id : Int, username : String, password : String){
        this.username = username
        this.password = password
        this.id = id
    }
    constructor(id : Int){
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun getUsername(): String {
        return username
    }
    fun getPassword(): String {
        return password
    }

}


