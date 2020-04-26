package com.frezzcoding.bolosassuncao.utils

interface ProductInputValidator {

    fun checkCurrentValidity(resource : String)
    fun checkInputValidity() : Boolean

}