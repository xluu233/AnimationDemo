package com.example.animationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animationdemo.databinding.ActivityMain2Binding
import com.example.animationdemo.databinding.ActivityMainBinding
import com.hi.dhl.binding.viewbind

class MainActivity2 : AppCompatActivity() {

    private val binding : ActivityMain2Binding by viewbind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }

}