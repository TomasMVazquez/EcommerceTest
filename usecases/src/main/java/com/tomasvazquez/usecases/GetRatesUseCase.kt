package com.tomasvazquez.usecases

import com.tomasvazquez.data.*
import com.tomasvazquez.data.repository.RateRepository
import com.tomasvazquez.domain.ExchangeRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetRatesUseCase(private val rateRepository: RateRepository):
    FlowUseCase<Unit,Either<List<ExchangeRate>,String>>(){

    override fun prepareFlow(input: Unit): Flow<Either<List<ExchangeRate>, String>> = flow {
        rateRepository.getRates()
            .onSuccess { emit(eitherSuccess(it)) }
            .onFailure { emit(eitherFailure(it)) }
    }
}