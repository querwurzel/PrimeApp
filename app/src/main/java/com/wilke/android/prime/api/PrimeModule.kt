package com.wilke.android.prime.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(ActivityComponent::class)
class PrimeModule {

    @Provides
    fun primeClient(): PrimeClient {
        return Retrofit.Builder()
            .baseUrl("https://prime.wilke-it.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(PrimeClient::class.java)
    }

}