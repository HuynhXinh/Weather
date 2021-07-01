package com.example.weather.base.adapter

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<D>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: D, position: Int)

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return itemView.context.getString(resId, *formatArgs)
    }

    protected fun getContext(): Context {
        return itemView.context
    }
}
