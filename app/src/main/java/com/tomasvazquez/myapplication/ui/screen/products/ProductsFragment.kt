package com.tomasvazquez.myapplication.ui.screen.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.tomasvazquez.domain.Transaction
import com.tomasvazquez.myapplication.R
import com.tomasvazquez.myapplication.databinding.FragmentProductsBinding
import com.tomasvazquez.myapplication.ui.screen.products.ProductsViewModel.*
import com.tomasvazquez.myapplication.util.Event
import com.tomasvazquez.myapplication.util.collectFlow
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : ScopeFragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel: ProductsViewModel by viewModel()

    private val adapter by lazy {
        ProductsRecyclerAdapter(
            Listener {
                viewModel.onTransactionClicked(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_products, container, false)

        adapter.submitList(emptyList())

        binding.recyclerProducts.adapter = adapter

        lifecycleScope.collectFlow(viewModel.model, ::updateUi)

        lifecycleScope.collectFlow(viewModel.navigation, ::goToDetail)

        return binding.root
    }

    private fun updateUi(model: UiModel) {
        /**
         * There is room to improve
         */
        when(model){
            is UiModel.Content -> {
                adapter.submitList(model.transactions)
                with(binding){
                    progress.visibility = View.GONE
                    infoState.visibility = View.GONE
                    recyclerProducts.visibility = View.VISIBLE
                }
            }
            is UiModel.ErrorWatcher -> {
                with(binding){
                    infoState.setInfoState(model.state)
                    progress.visibility = View.GONE
                    infoState.visibility = View.VISIBLE
                    recyclerProducts.visibility = View.GONE
                }
            }
            is UiModel.Loading -> {
                with(binding){
                    progress.visibility = View.VISIBLE
                    infoState.visibility = View.GONE
                    recyclerProducts.visibility = View.GONE
                }
            }
        }
    }

    private fun goToDetail(event: Event<Transaction?>){
        event.getContentIfNotHandled()?.let {
            findNavController().navigate(
                ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(it.sku)
            )
        }
    }

}