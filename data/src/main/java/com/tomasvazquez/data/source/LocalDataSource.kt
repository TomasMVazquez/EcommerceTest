package com.tomasvazquez.data.source

import com.tomasvazquez.domain.ExchangeRate
import com.tomasvazquez.domain.Transaction

interface LocalDataSource {

    suspend fun saveTransactions(items: List<Transaction>)

    suspend fun getTransactions(): List<Transaction>

    fun isTransactionEmpty(): Boolean

    suspend fun getRates(): List<ExchangeRate>

}