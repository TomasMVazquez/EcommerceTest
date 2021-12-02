package com.tomasvazquez.myapplication.data.network

import com.tomasvazquez.myapplication.data.network.model.Transaction
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface TransactionsApi {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("/transactions")
    fun getTransactionsAsync(): Deferred<Response<List<Transaction>>>

}