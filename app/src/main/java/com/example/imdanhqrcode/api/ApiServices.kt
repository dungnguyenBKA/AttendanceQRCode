package com.example.imdanhqrcode.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServices {
    companion object{

        private var api: Api? = null

        fun getInstance() : Api{

            return if(api != null){
                api!!
            }else{
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://dungqrcodediemdanh.000webhostapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                api = retrofit.create(Api::class.java)
                api!!
            }
        }
    }
}