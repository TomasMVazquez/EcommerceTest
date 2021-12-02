package com.tomasvazquez.data.repository

import com.tomasvazquez.data.*
import com.tomasvazquez.data.source.RemoteDataSource
import com.tomasvazquez.domain.ExchangeRate

class RateRepository(private val remoteDataSource: RemoteDataSource) {

    /**
     * TODO use Room to create a local
     */

    suspend fun getRates(): Either<List<ExchangeRate>, String> {
        return remoteDataSource.getRates()
    }

}