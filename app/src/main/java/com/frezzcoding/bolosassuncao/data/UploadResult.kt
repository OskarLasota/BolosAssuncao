package com.frezzcoding.bolosassuncao.data

import com.google.gson.annotations.SerializedName

data class UploadResult(var error : Boolean, var url : String,
                        var name : String)