package com.tomasvazquez.domain

data class Transaction (
    val sku: String,
    val amount: Double,
    val currency: String
)