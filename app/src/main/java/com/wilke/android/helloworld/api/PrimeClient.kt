package com.wilke.android.helloworld.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrimeClient {

    fun fetchMeta() {
        val metaCall = Prime.client().fetchMeta()

        metaCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}