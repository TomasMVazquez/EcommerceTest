package com.tomasvazquez.myapplication.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val sku: String,
    val amount: Double,
    val currency: String
)