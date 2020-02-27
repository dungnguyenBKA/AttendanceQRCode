package com.example.imdanhqrcode.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imdanhqrcode.R
import com.example.imdanhqrcode.model.Student
import kotlinx.android.synthetic.main.item_student.view.*

class ListStudentAdapter : RecyclerView.Adapter<ListStudentAdapter.ViewHolder>() {

    private var listStudent: ArrayList<Student> = ArrayList()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val studentName : TextView = view.name_student
        val studentId : TextView = view.id_student
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false))
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = listStudent[position]
        holder.studentId.text = student.id
        holder.studentName.text = student.name
    }

    fun updateList(listStudent: ArrayList<Student>?){
        if(listStudent != null){
            this.listStudent = listStudent
        }
    }
}