package com.tomasvazquez.myapplication.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "http://quiet-stone-2094.herokuapp.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

/**
 * Created seperate API Service to allow grow on app
 */
object TransactionApiService {
    val retrofitService: TransactionsApi by lazy {
        retrofit.create(TransactionsApi::class.java)
    }
}

object RateApiService {
    val retrofitService: RatesApi by lazy {
        retrofit.create(RatesApi::class.java)
    }
}