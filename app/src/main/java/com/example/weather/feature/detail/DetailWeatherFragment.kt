package com.example.weather.feature.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.databinding.FragmentDetailWeatherBinding
import com.example.weather.databinding.FragmentSearchWeatherBinding
import com.example.weather.feature.search.ItemWeather
import com.example.weather.util.viewBinding

class DetailWeatherFragment : Fragment(R.layout.fragment_detail_weather) {

    private val binding by viewBinding(FragmentDetailWeatherBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item: ItemWeather? = arguments?.getParcelable(ITEM)

        binding.tvDetail.text = "$item"

    }

    companion object {
        private const val ITEM = "item"

        fun toBundle(itemWeather: ItemWeather): Bundle {
            return bundleOf(
                ITEM to itemWeather
            )
        }
    }
}