package com.example.imdanhqrcode.view.student

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imdanhqrcode.R
import com.example.imdanhqrcode.api.Api
import com.example.imdanhqrcode.model.Response
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScanActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {

    private lateinit var mScannerView: ZBarScannerView
    private var studentName = ""
    private var studentId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        studentName = intent.getStringExtra("name") ?: ""
        studentId = intent.getStringExtra("id") ?: ""

        mScannerView = ZBarScannerView(this)
        setContentView(mScannerView)
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        // call api
        setContentView(R.layout.activity_scan)
        val strQr = rawResult?.contents

        if (strQr == null) {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dungqrcodediemdanh.000webhostapp.com/")
            .build()

        val api = retrofit.create(Api::class.java)

        println("$strQr, $studentId, $studentName")

        api.checkAttendanceVer2(strQr!!, studentId, studentName).enqueue(object :
            Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                finish()
            }

            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response>
            ) {

                val intent = Intent()

                if (response.body() == null) {
                    intent.putExtra("msg", "Fail to attend class")
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                    return
                }

                // call back to main activity
                intent.putExtra("msg", response.body()!!.msg)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })
    }
}
