package com.frezzcoding.bolosassuncao.utils

interface InputValidator {

    fun checkCurrentValidity(resource : String)
    fun checkInputValidity() : Boolean

}