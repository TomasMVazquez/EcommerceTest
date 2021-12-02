package com.tomasvazquez.myapplication.ui.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tomasvazquez.myapplication.R
import com.tomasvazquez.myapplication.databinding.FragmentProductDetailBinding
import com.tomasvazquez.myapplication.ui.customviews.InfoState
import com.tomasvazquez.myapplication.ui.screen.detail.DetailViewModel.*
import com.tomasvazquez.myapplication.ui.screen.detail.DetailViewModel.UiModel.*
import com.tomasvazquez.myapplication.ui.screen.products.Listener
import com.tomasvazquez.myapplication.ui.screen.products.ProductsRecyclerAdapter
import com.tomasvazquez.myapplication.util.CurrencyManager
import com.tomasvazquez.myapplication.util.collectFlow
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailFragment : ScopeFragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private val args by navArgs<ProductDetailFragmentArgs>()

    private val currencyManager = CurrencyManager()

    private val viewModel: DetailViewModel by viewModel {
        parametersOf(args.sku)
    }

    private val adapter by lazy {
        ProductsRecyclerAdapter(
            Listener {
                //TODO IF WE WANT TO DO SOMETHING
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_detail, container, false)

        if (args.sku == "-1") findNavController().popBackStack() else binding.sku = args.sku

        adapter.submitList(emptyList())

        binding.recyclerProducts.adapter = adapter

        lifecycleScope.collectFlow(viewModel.model, ::updateUi)

        return binding.root
    }

    private fun updateUi(model: UiModel) {
        /**
         * There is room to improve
         */
        when(model){
            is Rates -> {
                with(binding){
                    currencyManager.rates = model.rates
                    headerGroup.visibility = View.VISIBLE
                }
            }
            is Content -> {
                adapter.submitList(model.transactions)

                with(binding){
                    count.text = model.transactions.size.toString()
                    amount.text = currencyManager.calculateAmount(model.transactions,"EUR")
                    progress.visibility = View.GONE
                    infoState.visibility = View.GONE
                    recyclerProducts.visibility = View.VISIBLE
                }
            }
            is ErrorWatcher -> {
                when(model.state){
                    InfoState.RATES -> {
                        with(binding){
                            progress.visibility = View.GONE
                            infoState.visibility = View.GONE
                            headerGroup.visibility = View.GONE
                        }
                    }
                    else -> {
                        with(binding){
                            infoState.setInfoState(model.state)
                            progress.visibility = View.GONE
                            infoState.visibility = View.VISIBLE
                            headerGroup.visibility = View.GONE
                            recyclerProducts.visibility = View.GONE
                        }
                    }
                }
            }
            is Loading -> {
                with(binding){
                    progress.visibility = View.VISIBLE
                    infoState.visibility = View.GONE
                    headerGroup.visibility = View.GONE
                    recyclerProducts.visibility = View.GONE
                }
            }
        }
    }

}

