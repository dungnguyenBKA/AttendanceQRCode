package com.example.imdanhqrcode.api

import com.example.imdanhqrcode.model.CurrentString
import com.example.imdanhqrcode.model.Response
import com.example.imdanhqrcode.model.Student
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    // api 1
    @GET("string_post.php")
    fun postString(
        @Query("checkerStr") str: String
    ): Call<Response>

    // api 2 ver 2
    @GET("st_check_ver2.php")
    fun checkAttendanceVer2(
        @Query("reqStr") str: String,
        @Query("reqId") id: String,
        @Query("reqName") name: String
    ): Call<Response>

    // api 3
    @GET("st_print.php")
    fun getAllStudent(): Call<ArrayList<Student>>


    // api 6
    @GET("currentString.php")
    fun getCurrentString(): Call<CurrentString>
}