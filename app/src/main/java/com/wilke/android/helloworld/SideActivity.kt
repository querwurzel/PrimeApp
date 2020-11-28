package com.wilke.android.helloworld

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wilke.android.helloworld.api.Prime
import com.wilke.android.helloworld.databinding.ActivitySideBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SideActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(this.javaClass.name, "Creating side activity")

        binding = ActivitySideBinding.inflate(layoutInflater)

        fetchPersonalResults()

        setContentView(binding.root)
        setSupportActionBar(binding.tbBack2main)

        supportActionBar?.title = "Bring me home"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tbBack2main.setNavigationOnClickListener {
            this.onBackPressed()
            this.finishAffinity()
        }
    }

    private fun fetchPersonalResults() {
        val resultsCall = Prime.client().fetchResults()

        resultsCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    binding.textView.text = response.body()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(this.javaClass.name, t.message!!)
            }
        })
    }
}