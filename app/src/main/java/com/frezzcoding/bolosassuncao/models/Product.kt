package com.frezzcoding.bolosassuncao.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "basket_table")
class Product : Serializable{
    @PrimaryKey(autoGenerate = true)
    var unique : Int = 0;
    var id : Int = 0
    var url:String
    var name:String
    @Ignore var description:String
    @Ignore var encode : String
    var price : Double

    constructor(id : Int, url : String, name : String, description : String, encode : String, price : Double){
        this.id = id
        this.url = url
        this.name = name
        this.description = description
        this.encode = encode
        this.price = price
    }


    constructor() : this(0, "","", "", "", 0.0)

}
