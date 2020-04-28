package com.frezzcoding.bolosassuncao.models

import java.io.File
import java.io.Serializable

data class Product(val id:Int, var url:String, var name:String, var description:String, var encode : String, var price : Double) : Serializable
