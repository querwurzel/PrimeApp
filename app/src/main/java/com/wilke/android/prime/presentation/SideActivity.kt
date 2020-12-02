package com.wilke.android.prime.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.wilke.android.prime.R
import com.wilke.android.prime.api.PrimeClient
import com.wilke.android.prime.databinding.ActivitySideBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class SideActivity : AppCompatActivity() {

    @Inject
    protected lateinit var primeClient: PrimeClient

    private lateinit var binding: ActivitySideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(this.javaClass.name, "Creating side activity")

        binding = ActivitySideBinding.inflate(layoutInflater)

        fetchPersonalResults()

        setContentView(binding.root)
        setSupportActionBar(binding.tbBack2main)

        supportActionBar?.title = "Back"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tbBack2main.setNavigationOnClickListener {
            this.onBackPressed()
            this.finishAffinity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.i_share) {
            val shareIt = Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, binding.textView.text)
                .setType("text/plain")

            val shareIntent = Intent.createChooser(shareIt, "Share personal results")
            startActivity(shareIntent)
        }

        return true
    }

    private fun fetchPersonalResults() {
        val resultsCall = primeClient.fetchResults()

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