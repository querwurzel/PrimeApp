package com.wilke.android.prime.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.wilke.android.prime.R
import com.wilke.android.prime.api.PrimeClient
import com.wilke.android.prime.databinding.ActivityMainBinding
import com.wilke.android.prime.viewmodel.PrimeModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    protected lateinit var primeClient: PrimeClient

    private val myTag = this.javaClass.name

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(myTag, "Creating main activity")

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        this.setContentView(binding.root)
        //this.setContentView(R.layout.activity_main)

        val primeModel: PrimeModel by viewModels()

        primeModel.meta.observe(this, Observer {
            binding.tvMeta.text = it
        })

        binding.btResults.setOnClickListener {
            super.startActivity(Intent(this, SideActivity::class.java))
        }

        val prefs = this.getPreferences(Context.MODE_PRIVATE)
        val meta = prefs.getString("meta", "")
        primeModel.meta.value = meta



        binding.tvMeta.text = meta
        binding.btMeta.setOnClickListener {
            binding.indeterminateBar.visibility = View.VISIBLE
            refresh()
        }
    }

    private fun showSnackbar(text: String) {
        val view = this.findViewById<View>(R.id.cl_main)
        Snackbar.make(view, text, TimeUnit.SECONDS.toMillis(2).toInt()).show()

        // PORNO!! Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun refresh() {
        val primeModel: PrimeModel by viewModels()
        val metaCall = primeClient.fetchMeta()

        metaCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    primeModel.meta.postValue(response.body()) // postValue sounds like threadsafe

                    binding.indeterminateBar.visibility = View.GONE
                    showSnackbar("Refreshed")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(PrimeModel::class.simpleName, t.message!!)
            }
        })

    }

    override fun onPause() {
        super.onPause()

        val prefs = this.getPreferences(Context.MODE_PRIVATE)
        val primeModel: PrimeModel by viewModels()
        prefs.edit().putString("meta", primeModel.meta.value).apply()

    }
}