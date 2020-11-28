package com.wilke.android.helloworld

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.wilke.android.helloworld.databinding.ActivityMainBinding
import com.wilke.android.helloworld.viewmodel.HelloModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        this.setContentView(binding.root)

        val helloModel: HelloModel by viewModels()
        helloModel.name.observe(this, Observer {
            binding.tvHello.text = getString(R.string.hello_name, it)
        })

        binding.btNext.setOnClickListener {
            helloModel.name.value = "Hello Stefan"
        }


        
    }
}