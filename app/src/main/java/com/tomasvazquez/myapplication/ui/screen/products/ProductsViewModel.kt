package com.tomasvazquez.myapplication.ui.screen.products

import com.applications.toms.depormas.utils.ScopedViewModel
import com.tomasvazquez.data.onFailure
import com.tomasvazquez.data.onSuccess
import com.tomasvazquez.domain.Transaction
import com.tomasvazquez.myapplication.ui.customviews.InfoState
import com.tomasvazquez.myapplication.ui.screen.products.ProductsViewModel.UiModel.*
import com.tomasvazquez.myapplication.util.Event
import com.tomasvazquez.usecases.GetTransactionsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    uiDispatcher: CoroutineDispatcher,
): ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        object Loading: UiModel()
        class Content(val transactions: List<Transaction>): UiModel()
        class ErrorWatcher(val state: InfoState): UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(Loading)
    val model: StateFlow<UiModel> get() = _model

    private val _navigation = MutableStateFlow<Event<Transaction?>>(Event(null))
    val navigation: StateFlow<Event<Transaction?>> get() = _navigation

    init {
        _model.value = Loading
        fetchTransactions()
    }

    private fun fetchTransactions() {
        launch {
            getTransactionsUseCase.prepare(Unit).collect { result ->
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

    fun onTransactionClicked(transaction: Transaction) {
        _navigation.value = Event(transaction)
    }

}