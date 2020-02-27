package com.example.imdanhqrcode.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdanhqrcode.model.ListStudentResponse
import com.example.imdanhqrcode.model.Student
import com.example.imdanhqrcode.repository.ListStudentRepo

class ListStudentViewModel: ViewModel(), ListStudentRepo.OnRepoUpdateListener {
    var listStudentResponse : MutableLiveData<ListStudentResponse> = MutableLiveData()
    private var listStudentRepo: ListStudentRepo? = null

    init {
        listStudentRepo = ListStudentRepo.getInstance()
        listStudentRepo?.setOnRepoUpdateListener(this)
    }

    fun update(){
        // call method update in repo
        listStudentRepo?.update()
    }

    override fun onUpdated(listStudent: ArrayList<Student>) {
        this.listStudentResponse.postValue(ListStudentResponse(listStudent = listStudent))
    }

    override fun onFail(throwable: Throwable) {
        this.listStudentResponse.postValue(ListStudentResponse(throwable = throwable))
        // update error to ui ...
    }
}