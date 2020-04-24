package com.frezzcoding.bolosassuncao.data

import com.google.gson.annotations.SerializedName
import java.io.File

data class UploadBody(@SerializedName("name") var name : String, @SerializedName("image") var image : String,
                      @SerializedName("price") var price : Double, @SerializedName("desc") var desc : String)