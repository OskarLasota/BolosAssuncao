package com.frezzcoding.bolosassuncao.data

import com.google.gson.annotations.SerializedName

data class UploadResult(@SerializedName("error") var error : String, @SerializedName("url") var url : String,
                        @SerializedName("name") var name : String)