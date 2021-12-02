package com.tomasvazquez.data.source

import com.tomasvazquez.data.Either
import com.tomasvazquez.domain.ExchangeRate
import com.tomasvazquez.domain.Transaction

interface RemoteDataSource {

    suspend fun getTransactions(): Either<List<Transaction>,String>

    suspend fun getRates(): Either<List<ExchangeRate>,String>

}