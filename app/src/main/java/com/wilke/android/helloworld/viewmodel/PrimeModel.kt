package com.wilke.android.helloworld.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wilke.android.helloworld.api.Prime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrimeModel : ViewModel() {

    fun refresh() {
        val metaCall = Prime.client().fetchMeta()

        metaCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    meta.postValue(response.body()) // postValue sounds like threadsafe
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(PrimeModel::class.simpleName, t.message!!)
            }
        })

    }

    var meta = MutableLiveData<String>()

}