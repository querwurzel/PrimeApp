package com.wilke.android.helloworld.api

import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface Prime {

    companion object {
        fun client() : Prime = Retrofit.Builder()
            .baseUrl("https://prime.wilke-it.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(Prime::class.java)
    }

    @GET("/results")
    fun fetchResults(): Call<String>

    @GET("/results/meta")
    fun fetchMeta(): Call<String>

}
