package com.example.administrator.myapplication

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.myapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        tv.text="ffff"
        val binding: ActivityMainBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        val m:MyBean=MyBean()
        m.name="ddd"
        m.heiget="xxxxx"
        binding.data=m
    }
}
