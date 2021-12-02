package com.tomasvazquez.myapplication.data.network

import com.tomasvazquez.myapplication.data.network.model.Rate
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RatesApi {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("/rates")
    fun getRatesAsync(): Deferred<Response<List<Rate>>>
}