package com.tomasvazquez.myapplication.di

import android.app.Application
import com.tomasvazquez.data.repository.RateRepository
import com.tomasvazquez.data.repository.TransactionRepository
import com.tomasvazquez.data.source.LocalDataSource
import com.tomasvazquez.data.source.RemoteDataSource
import com.tomasvazquez.myapplication.data.database.RoomDataSource
import com.tomasvazquez.myapplication.data.database.TransactionDatabase
import com.tomasvazquez.myapplication.data.network.NetworkDataSource
import com.tomasvazquez.myapplication.ui.screen.detail.DetailViewModel
import com.tomasvazquez.myapplication.ui.screen.detail.ProductDetailFragment
import com.tomasvazquez.myapplication.ui.screen.products.ProductsFragment
import com.tomasvazquez.myapplication.ui.screen.products.ProductsViewModel
import com.tomasvazquez.usecases.GetRatesUseCase
import com.tomasvazquez.usecases.GetSkuTransactionsUseCase
import com.tomasvazquez.usecases.GetTransactionsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single { TransactionDatabase.getInstance(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { NetworkDataSource() }
    single<CoroutineDispatcher> { Dispatchers.Main }
}

private val dataModule = module {

    factory { TransactionRepository(get(),get()) }
    factory { RateRepository(get()) }
}

private val scopesModule = module {
    scope(named<ProductsFragment>()) {
        viewModel { ProductsViewModel(get(),get()) }
        scoped { GetTransactionsUseCase(get()) }
    }

    scope(named<ProductDetailFragment>()) {
        viewModel { (sku: String) -> DetailViewModel(sku,get(),get(),get()) }
        scoped { GetRatesUseCase(get()) }
        scoped { GetSkuTransactionsUseCase(get()) }
    }
}