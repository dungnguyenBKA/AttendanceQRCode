package com.example.imdanhqrcode.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var msg: String
)