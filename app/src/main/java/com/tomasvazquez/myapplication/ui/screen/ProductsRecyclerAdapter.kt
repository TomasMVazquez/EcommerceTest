package com.tomasvazquez.myapplication.ui.screen.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tomasvazquez.domain.Transaction
import com.tomasvazquez.myapplication.databinding.RecyclerItemProductBinding

class ProductsRecyclerAdapter(private val clickListener: Listener):
    ListAdapter<Transaction,ProductsRecyclerAdapter.ViewHolder>(ClassDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it,clickListener)
        }
    }

    class ViewHolder private constructor(val binding: RecyclerItemProductBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Transaction, listener: Listener) {
            with(binding){
                transaction = item
                clickListener = listener
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =  LayoutInflater.from(parent.context)
                val binding = RecyclerItemProductBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

    class ClassDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.sku == newItem.sku
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }

}

class Listener(val clickListener: (transaction: Transaction) -> Unit){
    fun onClick(transaction: Transaction) = clickListener(transaction)
}
