package com.example.imdanhqrcode

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.imdanhqrcode.view.master.MasterActivity
import com.example.imdanhqrcode.view.student.ScanActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val requestCodePermission: Int = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("activity create")
        setContentView(R.layout.activity_main)

        checkAndRequestPermission()

    }

    private fun checkAndRequestPermission() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestCodePermission
            )
        } else {
            createView()
        }
    }

    private fun createView() {
        edit_id.afterTextChanged {
            if (!isIdValid(it)) {
                edit_id.error = "Id must be 8 characters"
            }
        }

        edit_name.afterTextChanged {
            if (!isNamevalid(it)) {
                edit_name.error = "Name must not be empty"
            }
        }

        scan_btn.setOnClickListener {
            val id = edit_id.text.toString()
            val name = edit_name.text.toString()

            if (!isIdValid(id)) {
                edit_id.error = "Id must be 8 characters"
                return@setOnClickListener
            }

            if (!isNamevalid(name)) {
                edit_name.error = "Name must not be empty"
                return@setOnClickListener
            }

            if (!isLocationValid()) {
                return@setOnClickListener
            }

            val intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("id", id)
            startActivityForResult(intent, 111)
        }

        master_fragment_btn.setOnClickListener {
            // check gi do

            val intent = Intent(this, MasterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == requestCodePermission) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED || grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                // fail
                Toast.makeText(this, "App can not be used without permission", Toast.LENGTH_SHORT)
                    .show()
                finish()
            } else {
                createView()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            // some code
            Toast.makeText(this, data?.getStringExtra("msg"), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * extention funtion of edit text to simply use after text change
     */

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }


    private fun isLocationValid(): Boolean {
        val localLocation = getLastKnownLocation()

        if (localLocation == null) {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
            return false
        }

        Toast.makeText(
            this,
            "${localLocation.longitude} ${localLocation.latitude}",
            Toast.LENGTH_SHORT
        ).show()
        return true
    }

    private fun isIdValid(id: String): Boolean {
        return id.length == 8
    }

    private fun isNamevalid(name: String): Boolean {
        return name.isNotBlank()
    }


    //  return a local location, if no provider then return null
    private fun getLastKnownLocation(): Location? {
        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }

        val providers: List<String> = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val l: Location = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) { // Found best last known location: %s", l);
                bestLocation = l
            }
        }
        return bestLocation
    }
}
