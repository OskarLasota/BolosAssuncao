package com.frezzcoding.bolosassuncao.models

class User {

    var id = 0
    lateinit var username: String
    lateinit var password : String
    lateinit var email : String

    constructor(username : String, password : String){
        this.username = username
        this.password = password
        this.id = id
    }
    constructor(id : Int){
        this.id = id
    }
    constructor(username : String, password: String, email :String){
        this.username = username
        this.password = password
        this.email = email
    }






}


