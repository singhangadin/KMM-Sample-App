package com.github.angads25.kmmsampleapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.dummyNetworkCall("galaxy", "1")
        viewModel.liveData.observe(this, {
            findViewById<TextView>(R.id.text_view).text = it?:""
        })
    }
}
