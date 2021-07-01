package com.example.weather.feature.search

import android.view.ViewGroup
import com.example.weather.R
import com.example.weather.base.adapter.BaseViewHolder
import com.example.weather.databinding.ItemWeatherBinding
import com.example.weather.util.get

class WeatherViewHolderFactory(
    private val onClickItemListener: ((ItemWeather) -> Unit)? = null
) {

    fun create(parent: ViewGroup, viewType: Int): ItemWeatherViewHolder {
        return ItemWeatherViewHolder(
            binding = parent[ItemWeatherBinding::inflate]
        )
    }

    inner class ItemWeatherViewHolder(val binding: ItemWeatherBinding) :
        BaseViewHolder<ItemWeather>(binding.root) {

        override fun bind(data: ItemWeather, position: Int) = with(binding) {
            tvDate.text = getString(R.string.text_date, data.date)
            tvAveTemp.text = getString(R.string.text_ave_temp, data.aveTemp)
            tvPressure.text = getString(R.string.text_pressure, data.pressure)
            tvHumidity.text = getString(R.string.text_humidity, data.humidity)
            tvDesc.text = getString(R.string.text_desc, data.desc)

            root.setOnClickListener {
                onClickItemListener?.invoke(data)
            }
        }
    }
}
