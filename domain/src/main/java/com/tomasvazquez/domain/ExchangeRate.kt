package com.tomasvazquez.domain

data class ExchangeRate(
    val from: String,
    val to: String,
    val rate: Double
)