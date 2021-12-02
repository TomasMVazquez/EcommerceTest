package com.tomasvazquez.myapplication.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.tomasvazquez.myapplication.R
import com.tomasvazquez.myapplication.databinding.ItemInfoStateBinding

enum class  InfoState {
    NETWORK_ERROR,
    EMPTY,
    RATES,
    OTHER
}

class ItemInfoStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ItemInfoStateBinding

    init {
        ItemInfoStateBinding.inflate(LayoutInflater.from(context),this,true).also{
            binding = it
        }
    }

    fun setInfoState(infoState: InfoState){
        with(binding){
            /**
             * TODO Change imgs based on error
             */
            when(infoState){
                InfoState.NETWORK_ERROR -> {
                    stateImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_error_outline))
                    stateText.text = context.getString(R.string.conection_error)
                }
                InfoState.OTHER -> {
                    stateImg.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_error_outline))
                    stateText.text = context.getString(R.string.other)
                }
                InfoState.EMPTY -> {
                    stateImg.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_error_outline))
                    stateText.text = context.getString(R.string.emtpy)
                }
            }
        }
    }
    
}