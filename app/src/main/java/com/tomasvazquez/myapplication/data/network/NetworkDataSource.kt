package com.tomasvazquez.myapplication.data.network

import com.tomasvazquez.data.Either
import com.tomasvazquez.data.eitherFailure
import com.tomasvazquez.data.eitherSuccess
import com.tomasvazquez.data.source.RemoteDataSource
import com.tomasvazquez.domain.ExchangeRate
import com.tomasvazquez.domain.Transaction
import com.tomasvazquez.myapplication.data.toDomain

class NetworkDataSource : RemoteDataSource {

    override suspend fun getTransactions(): Either<List<Transaction>, String> {
        return try {
            val request = TransactionApiService.retrofitService.getTransactionsAsync().await()
            if (request.isSuccessful){
                val response = request.body()?.map { it.toDomain() } ?: emptyList()
                eitherSuccess(response)
            } else {
                eitherFailure("Service error")
            }
        }catch (t: Throwable) {
           return eitherFailure(t.message ?: "")
        }
    }

    override suspend fun getRates(): Either<List<ExchangeRate>, String> {
        return try {
            val request = RateApiService.retrofitService.getRatesAsync().await()
            if (request.isSuccessful){
                val response = request.body()?.map { it.toDomain() } ?: emptyList()
                eitherSuccess(response)
            } else {
                eitherFailure("Service error")
            }
        }catch (t: Throwable) {
            return eitherFailure(t.message ?: "")
        }
    }

}