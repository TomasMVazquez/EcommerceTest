package com.tomasvazquez.myapplication.util

import com.tomasvazquez.domain.ExchangeRate
import com.tomasvazquez.domain.Transaction
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyManager {

    var rates: List<ExchangeRate> = listOf()

    fun calculateAmount(
        transactions: List<Transaction>,
        toCurrency: String,
    ): String {
        var totalAmount = 0.0
        transactions.forEach {
            totalAmount += if (it.currency == toCurrency) {
                it.amount
            } else {
                calculateTransactionRate(
                    from = it.currency,
                    to = toCurrency
                ) * it.amount
            }
        }
        return BigDecimal(totalAmount).setScale(2,RoundingMode.HALF_EVEN).toString()
    }

    private fun calculateTransactionRate(from: String, to: String): Double {
        return getRate(from, to) ?: 0.0
    }

    private fun getDirectRate(from: String, to: String): Double? {
        rates.forEach {
            if (it.from == from && it.to == to) {
                return it.rate
            }
        }
        return null
    }

    private fun getRate(
        from: String,
        to: String,
        rate: Double = 1.0,
        converted: HashSet<String> = hashSetOf(),
    ): Double? {
        getDirectRate(from, to)?.let {
            return rate * it
        } ?: run {
            rates.forEach {
                if (it.from == from && (it.to in converted).not()) {
                    converted.add(from)
                    getRate(
                        from = it.to,
                        to = to,
                        rate = rate * it.rate,
                        converted = converted
                    )?.let { newRate ->
                        return newRate
                    }
                }
            }
        }
        return null
    }

}