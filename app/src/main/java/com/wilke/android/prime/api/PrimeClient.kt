package com.wilke.android.prime.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface PrimeClient {

    @GET("/results")
    fun fetchResults(): Call<String>

    @GET("/results/meta")
    fun fetchMeta(): Call<String>

}
