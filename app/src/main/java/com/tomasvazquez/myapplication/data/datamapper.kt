package com.tomasvazquez.myapplication.data

import com.tomasvazquez.domain.ExchangeRate
import com.tomasvazquez.myapplication.data.network.model.Transaction as NetworkTransaction
import com.tomasvazquez.myapplication.data.network.model.Rate as NetworkRate
import com.tomasvazquez.myapplication.data.database.model.Transaction as DbTransaction
import com.tomasvazquez.domain.Transaction

fun NetworkTransaction.toDomain(): Transaction = Transaction(sku,amount.toDouble(),currency)

fun DbTransaction.toDomain(): Transaction = Transaction(sku,amount,currency)
fun Transaction.toDb(): DbTransaction = DbTransaction(0,sku,amount,currency)

fun NetworkRate.toDomain(): ExchangeRate = ExchangeRate(from,to,rate.toDouble())