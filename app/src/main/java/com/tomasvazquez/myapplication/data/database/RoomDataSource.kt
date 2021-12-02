package com.tomasvazquez.myapplication.data.database

import com.tomasvazquez.data.source.LocalDataSource
import com.tomasvazquez.domain.ExchangeRate
import com.tomasvazquez.domain.Transaction
import com.tomasvazquez.myapplication.data.toDb
import com.tomasvazquez.myapplication.data.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: TransactionDatabase): LocalDataSource {

    private val dao = db.transactionDatabaseDao

    override suspend fun saveTransactions(items: List<Transaction>) {
        dao.insert(*items.map{ it.toDb() }.toTypedArray())
    }

    override suspend fun getTransactions(): List<Transaction> {
        return withContext(Dispatchers.IO) {
            dao.getAll().map { it.toDomain() }
        }
    }

    override fun isTransactionEmpty(): Boolean = dao.transactionsCount() == 0

    override suspend fun getRates(): List<ExchangeRate> {
        TODO("Not yet implemented")
    }


}