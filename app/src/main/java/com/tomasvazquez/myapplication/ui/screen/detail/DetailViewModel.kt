package com.tomasvazquez.myapplication.ui.screen.detail

import com.applications.toms.depormas.utils.ScopedViewModel
import com.tomasvazquez.data.onFailure
import com.tomasvazquez.data.onSuccess
import com.tomasvazquez.domain.ExchangeRate
import com.tomasvazquez.domain.Transaction
import com.tomasvazquez.myapplication.ui.customviews.InfoState
import com.tomasvazquez.myapplication.ui.screen.detail.DetailViewModel.UiModel.*
import com.tomasvazquez.usecases.GetRatesUseCase
import com.tomasvazquez.usecases.GetSkuTransactionsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
    private val sku: String,
    private val getSkuTransactionsUseCase: GetSkuTransactionsUseCase,
    private val getRatesUseCase: GetRatesUseCase,
    uiDispatcher: CoroutineDispatcher,
) :ScopedViewModel(uiDispatcher) {


    sealed class UiModel {
        object Loading: UiModel()
        class Content(val transactions: List<Transaction>): UiModel()
        class Rates(val rates: List<ExchangeRate>): UiModel()
        class ErrorWatcher(val state: InfoState): UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(Loading)
    val model: StateFlow<UiModel> get() = _model

    init {
        fetchRates()
        fetchTransactions()
    }

    private fun fetchRates() {
        launch {
            getRatesUseCase.prepare(Unit).collect { result ->
                result.onSuccess {
                    _model.value = Rates(it)
                }
                result.onFailure {
                    _model.value = ErrorWatcher(InfoState.RATES)
                }
            }
        }
    }

    private fun fetchTransactions() {
        launch {
            getSkuTransactionsUseCase.prepare(sku).collect { result ->
                result.onSuccess { list ->
                    if (list.isNotEmpty())
                        _model.value = Content(list)
                    else
                        _model.value = ErrorWatcher(InfoState.EMPTY)
                }
                result.onFailure { error ->
                    /**
                     * TODO Check error type and show accordingly
                     */
                    _model.value = ErrorWatcher(InfoState.OTHER)
                }
            }
        }
    }
}