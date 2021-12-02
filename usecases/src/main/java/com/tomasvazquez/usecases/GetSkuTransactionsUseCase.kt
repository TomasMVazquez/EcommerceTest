package com.tomasvazquez.usecases

import com.tomasvazquez.data.*
import com.tomasvazquez.data.repository.TransactionRepository
import com.tomasvazquez.domain.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.*

class GetSkuTransactionsUseCase(private val transactionRepository: TransactionRepository):
    FlowUseCase<String,Either<List<Transaction>,String>>(){

    override fun prepareFlow(input: String): Flow<Either<List<Transaction>, String>> = flow {
        transactionRepository.getTransactions()
            .onSuccess { list ->
                emit(eitherSuccess(list.filter { it.sku == input }))
            }
            .onFailure { emit(eitherFailure(it)) }
    }
}