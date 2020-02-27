package com.example.imdanhqrcode.model

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("name")
    var name: String,
    @SerializedName("student_id")
    var id: String
)