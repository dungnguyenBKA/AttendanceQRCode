package com.example.imdanhqrcode.view.master

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.imdanhqrcode.R
import com.example.imdanhqrcode.api.Api
import com.example.imdanhqrcode.model.Response
import kotlinx.android.synthetic.main.fragment_post_string.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostStringFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_string, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        post_string_edt.afterTextChanged {
            if(!isStringValid(it)){
                post_string_edt.error = "String must not be blank"
            }
        }

        post_btn.setOnClickListener{
            val s = post_string_edt.text.toString()
            if(!isStringValid(s)){
                return@setOnClickListener
            }

            // call api 1
            requestPostString(s)
        }
    }

    private fun requestPostString(str: String){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dungqrcodediemdanh.000webhostapp.com/")
            .build()

        val api = retrofit.create(Api::class.java)

        api.postString(str).enqueue(object :
            Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                throw t
            }

            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response>
            ) {
                if(response.isSuccessful){
                    if(response.body() != null && response.body()!!.code == 200){
                        Toast.makeText(requireActivity(), response.body()!!.msg, Toast.LENGTH_SHORT).show()

                        // back
                        requireActivity().supportFragmentManager.popBackStack()
                    }

                    if(response.body() != null && response.body()!!.code == 404){
                        Toast.makeText(requireActivity(), response.body()!!.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun isStringValid(s: String): Boolean{
        return s.isNotBlank()
    }

    companion object{
        fun newInstance() = PostStringFragment()
    }

    /**
     *  extend function
    */

    private fun EditText.afterTextChanged(after: (String)-> Unit){
        this.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                after.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }
}