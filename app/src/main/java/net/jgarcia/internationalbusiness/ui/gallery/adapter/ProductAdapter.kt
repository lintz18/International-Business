package net.jgarcia.internationalbusiness.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.jgarcia.internationalbusiness.data.Transaction
import net.jgarcia.internationalbusiness.databinding.ItemTransactionBinding

class ProductAdapter(private val listener: OnItemClickListener) :
PagingDataAdapter<Transaction, ProductAdapter.TransactionViewHolder>(TRANSACTION_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition //Necesitamos la posicion
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position) // Recuperamos el objecto por la posición
                    if (item != null) {
                        listener.OnItemClick(item)
                    }
                }
            }
        }

        fun bind(transaction: Transaction) {
            binding.apply {
                textViewSku.text = transaction.sku
                textViewAmount.text = transaction.amount
                textViewCurrency.text = transaction.currency
            }
        }
    }

    interface OnItemClickListener {
        fun OnItemClick(transaction: Transaction)
    }

    companion object {
        private val TRANSACTION_COMPARATOR = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) =
                oldItem.sku == newItem.sku

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) =
                oldItem == newItem
        }
    }
}
