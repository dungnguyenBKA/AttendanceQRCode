package com.example.imdanhqrcode.repository

import com.example.imdanhqrcode.api.ApiServices
import com.example.imdanhqrcode.model.CurrentString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryRepo {
    private var listener: OnResultListener? = null

    fun setOnResultListener(listener: OnResultListener){
        this.listener = listener
    }

    fun getCurrentString(){
        val api = ApiServices.getInstance()

        api.getCurrentString().enqueue(object : Callback<CurrentString> {
            override fun onFailure(call: Call<CurrentString>, t: Throwable) {
                println("On Fail")
                throw t
            }

            override fun onResponse(call: Call<CurrentString>, response: Response<CurrentString>) {
                if(response.isSuccessful && response.body() != null){
                    listener?.onSuccessGetCurrentString(response.body()!!.data)
                }
            }
        })
    }

    interface OnResultListener{
        fun onSuccessGetCurrentString(s: String)
        fun onFailGetCurrentString()
    }
}