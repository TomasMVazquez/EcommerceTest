package com.tomasvazquez.data.repository

import com.tomasvazquez.data.*
import com.tomasvazquez.data.source.LocalDataSource
import com.tomasvazquez.data.source.RemoteDataSource
import com.tomasvazquez.domain.Transaction

class TransactionRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun getTransactions(): Either<List<Transaction>, String>  {
        if( localDataSource.isTransactionEmpty() ){
            val list = remoteDataSource.getTransactions()
            list.onSuccess {
                localDataSource.saveTransactions(it)
            }
            list.onFailure {
                return eitherFailure(it)
            }
        }
        return eitherSuccess(localDataSource.getTransactions())
    }

}