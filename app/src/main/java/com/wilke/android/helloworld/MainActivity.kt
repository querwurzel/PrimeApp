package com.wilke.android.helloworld

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.wilke.android.helloworld.databinding.ActivityMainBinding
import com.wilke.android.helloworld.viewmodel.PrimeModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val myTag = this.javaClass.name

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(myTag, "Creating main activity")

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        this.setContentView(binding.root)

        val primeModel: PrimeModel by viewModels()

        primeModel.meta.observe(this, Observer {
            binding.tvMeta.text = it

            binding.indeterminateBar.visibility = View.GONE
            showSnackbar("Refreshed")
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
            primeModel.refresh()
        }
    }

    private fun showSnackbar(text: String) {
        val view = this.findViewById<View>(R.id.cl_main)
        Snackbar.make(view, text, TimeUnit.SECONDS.toMillis(2).toInt()).show()

        // PORNO!! Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()

        val prefs = this.getPreferences(Context.MODE_PRIVATE)
        val primeModel: PrimeModel by viewModels()
        prefs.edit().putString("meta", primeModel.meta.value).apply()

    }
}