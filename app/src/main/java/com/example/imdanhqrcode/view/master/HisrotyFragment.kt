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
import com.example.imdanhqrcode.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_hisroty.*

class HisrotyFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var historyViewModel: HistoryViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hisroty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        historyViewModel = ViewModelProvider(requireActivity()).get(HistoryViewModel::class.java)

        currentString?.text = historyViewModel?.currentString?.value ?: ""

        historyViewModel?.currentString?.observe(requireActivity(), Observer {
            currentString?.text = it
        })

        historyViewModel?.fetchCurrentString()

        // fetch data
        history_swipe_layout.setOnRefreshListener(this)
    }

    companion object {
        fun newInstance() = HisrotyFragment()
    }

    override fun onRefresh() {
        historyViewModel?.fetchCurrentString()

        history_swipe_layout.isRefreshing = false
    }
}
