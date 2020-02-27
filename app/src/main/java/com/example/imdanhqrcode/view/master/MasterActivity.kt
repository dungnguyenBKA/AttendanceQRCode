package com.example.imdanhqrcode.view.master

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.imdanhqrcode.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_master.*

class MasterActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)

        bottom_nav_bar.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_view, PostStringFragment.newInstance())
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment : Fragment? = null
        when(item.itemId){
            R.id.item_list -> fragment = ListStudentFragment.newInstance()
            R.id.item_post -> fragment = PostStringFragment.newInstance()
            R.id.item_history -> fragment = HisrotyFragment.newInstance()
        }

        if(fragment == null) return false

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_view, fragment)
            .commit()

        return true
    }
}
