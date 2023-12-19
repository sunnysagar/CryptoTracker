package com.matrix.crypto.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matrix.crypto.data.ItemEntity
import com.matrix.crypto.databinding.ListItemBinding


class ItemsListAdapter: RecyclerView.Adapter<ItemsListAdapter.ItemsViewHolder>() {

    private var items = mutableListOf<ItemEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsList(issues: MutableList<ItemEntity>) {
        this.items = issues.toMutableList()
        notifyDataSetChanged()
    }

    inner class ItemsViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val currentNode = items[position]

        holder.binding.apply {
            coinName.text = currentNode.coinName
            exchangeRate.text = String.format("%.6f", currentNode.exchangeRate)
            Glide.with(holder.itemView.context).load(currentNode.iconUrl).into(holder.binding.coinIcon)

        }
    }

}