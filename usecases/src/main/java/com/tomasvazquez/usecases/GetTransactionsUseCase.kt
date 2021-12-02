package com.tomasvazquez.usecases

import com.tomasvazquez.data.*
import com.tomasvazquez.data.repository.TransactionRepository
import com.tomasvazquez.domain.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTransactionsUseCase(private val transactionRepository: TransactionRepository):
    FlowUseCase<Unit,Either<List<Transaction>,String>>(){

    override fun prepareFlow(input: Unit): Flow<Either<List<Transaction>, String>> = flow {
        transactionRepository.getTransactions()
            .onSuccess { emit(eitherSuccess(it)) }
            .onFailure { emit(eitherFailure(it)) }
    }
}