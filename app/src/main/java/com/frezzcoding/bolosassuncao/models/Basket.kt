package com.frezzcoding.bolosassuncao.models

import androidx.room.Entity


@Entity(tableName = "basket_table")
class Basket {

    lateinit var product : Product

}