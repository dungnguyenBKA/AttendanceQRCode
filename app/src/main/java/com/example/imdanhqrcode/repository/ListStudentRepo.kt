package com.example.imdanhqrcode.repository

import com.example.imdanhqrcode.api.ApiServices
import com.example.imdanhqrcode.model.ListStudentResponse
import com.example.imdanhqrcode.model.Student
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListStudentRepo private constructor() {
    private var listener: OnRepoUpdateListener?=null

    fun update() {
        val api = ApiServices.getInstance()

        api.getAllStudent().enqueue(object : Callback<ArrayList<Student>>{
            override fun onFailure(call: Call<ArrayList<Student>>, t: Throwable) {
                // pass throwable to responce variable
                listener?.onFail(t)
            }

            override fun onResponse(
                call: Call<ArrayList<Student>>,
                response: Response<ArrayList<Student>>
            ) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        listener?.onUpdated(response.body()!!)
                    }else{
                        // pass a blank-array of student
                        listener?.onUpdated(ArrayList())
                    }
                }else{
                    listener?.onFail(Throwable("response unsuccess"))
                }
            }
        })
    }

    // singleton
    companion object{
        private var listStudentRepo : ListStudentRepo? = null
        fun getInstance() = listStudentRepo ?: ListStudentRepo()
    }

    fun setOnRepoUpdateListener(mOnRepoUpdateListener: OnRepoUpdateListener){
        this.listener = mOnRepoUpdateListener
    }

    interface OnRepoUpdateListener{
        fun onUpdated(listStudent: ArrayList<Student>)
        fun onFail(throwable: Throwable)
    }
}