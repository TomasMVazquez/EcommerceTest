package com.tomasvazquez.myapplication.data.database

import androidx.room.*
import com.tomasvazquez.myapplication.data.database.model.Transaction

@Dao
interface TransactionDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: Transaction)

    @Query("SELECT * FROM transaction_table")
    fun getAll(): List<Transaction>

    @Query("SELECT COUNT(sku) FROM  transaction_table")
    fun transactionsCount(): Int

}