package com.example.imdanhqrcode.view.master


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.imdanhqrcode.R
import com.example.imdanhqrcode.adapter.ListStudentAdapter
import com.example.imdanhqrcode.viewmodel.ListStudentViewModel
import kotlinx.android.synthetic.main.fragment_fetch_list_student.*

class ListStudentFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    // view model
    private var listStudentViewModel: ListStudentViewModel?= null
    private var listStudentAdapter : ListStudentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fetch_list_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listStudentViewModel = ViewModelProvider(requireActivity()).get(ListStudentViewModel::class.java)
        listStudentAdapter = ListStudentAdapter()
        listStudentAdapter?.updateList(listStudentViewModel?.listStudentResponse?.value?.listStudent)

        fetchList()

        list_student.adapter = listStudentAdapter

        swipe_layout.setOnRefreshListener(this)

        listStudentViewModel?.listStudentResponse?.observe(requireActivity(), Observer {
            progressBar2?.visibility = View.INVISIBLE

            if(it.listStudent != null){
                error_frag?.visibility = View.GONE
                list_student?.visibility = View.VISIBLE
                total?.visibility = View.VISIBLE

                total?.text = "Total: ${it.listStudent?.size}"

                listStudentAdapter?.updateList(it.listStudent)
                listStudentAdapter?.notifyDataSetChanged()
                list_student?.smoothScrollToPosition((it.listStudent?.size ?: 1) -1)
            }else{
                // show a error to user
                list_student?.visibility = View.GONE
                total?.visibility = View.GONE
                error_frag?.visibility = View.VISIBLE

                list_student_error_text?.text = it.throwable.toString()
            }
        })
    }

    private fun fetchList() {
        listStudentViewModel?.update()
    }

    companion object {
        fun newInstance() = ListStudentFragment()
    }

    override fun onRefresh() {
        // update list student by calling method in viewmodel
        listStudentViewModel?.update()
        swipe_layout.isRefreshing = false
    }
}
