package com.example.weather.feature.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weather.R
import com.example.weather.base.adapter.DifferentAdapter
import com.example.weather.databinding.FragmentSearchWeatherBinding
import com.example.weather.util.show
import com.example.weather.util.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchWeatherFragment : Fragment(R.layout.fragment_search_weather) {

    private val weatherViewModel: WeatherViewModel by viewModel()

    private val binding by viewBinding(FragmentSearchWeatherBinding::bind)

    private val searchForeCaseNavigator: SearchForeCaseNavigator by inject()

    private val adapter = DifferentAdapter(
        viewHolderFactory = WeatherViewHolderFactory(
            onClickItemListener = { searchForeCaseNavigator.openDetail() }
        )::create
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observer()

    }

    private fun setupView() = with(binding) {

        btnGetWeather.setOnClickListener {
            weatherViewModel.search(etSearch.text.toString())
        }

        rvResult.adapter = adapter
        rvResult.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun observer() = with(weatherViewModel) {
        loading.observe(viewLifecycleOwner) {
            binding.tvError.show(false)
            binding.loading.show(it)
        }

        error.observe(viewLifecycleOwner) {
            binding.tvError.show(true)
            binding.tvError.text = "$it"
            adapter.submitList(emptyList())
        }

        onSearchSuccess.observe(viewLifecycleOwner) {
            binding.tvError.show(false)
            adapter.submitList(it)
        }
    }

}