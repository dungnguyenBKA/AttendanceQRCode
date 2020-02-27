package com.example.imdanhqrcode.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdanhqrcode.repository.HistoryRepo

class HistoryViewModel : ViewModel(), HistoryRepo.OnResultListener{
    var currentString: MutableLiveData<String> = MutableLiveData()

    var repo = HistoryRepo()


    init {
        repo.setOnResultListener(this)
    }

    fun fetchCurrentString(){
        repo.getCurrentString()
    }

    override fun onSuccessGetCurrentString(s: String) {
        currentString.postValue(s)
    }

    override fun onFailGetCurrentString() {
        currentString.postValue("")
    }
}