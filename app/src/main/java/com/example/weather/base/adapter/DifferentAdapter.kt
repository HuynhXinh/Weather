package com.example.weather.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class DifferentAdapter<D : ItemDifferent, VH : BaseViewHolder<D>>(
    private val viewHolderFactory: (parent: ViewGroup, viewType: Int) -> VH,
) : ListAdapter<D, VH>(
    AsyncDifferConfig.Builder<D>(
        object : DiffUtil.ItemCallback<D>() {
            override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
                return oldItem.areItemsTheSame(newItem)
            }

            override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
                return oldItem.areContentsTheSame(newItem)
            }
        }
    ).build()
) {
    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return viewHolderFactory.invoke(parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), position)
    }
}
