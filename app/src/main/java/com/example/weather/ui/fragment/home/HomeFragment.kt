package com.example.weather.ui.fragment.home

import android.*
import androidx.activity.result.contract.*
import com.example.weather.R
import com.example.weather.appbase.*
import com.example.weather.appbase.utils.*
import com.example.weather.databinding.*
import com.example.weather.util.*
import org.koin.androidx.viewmodel.ext.android.*

class HomeFragment : FragmentBaseMVVM<HomeViewModel, FragmentHomeBinding>() {

	override val viewModel: HomeViewModel by viewModel()
	override val binding: FragmentHomeBinding by viewBinding()

	override fun onView() {
		super.onView()
		val locationPermissionRequest = activity?.registerForActivityResult(
			ActivityResultContracts.RequestMultiplePermissions()
		) { permissions ->
			when {
				permissions.getValue(Manifest.permission.ACCESS_FINE_LOCATION) -> {
					viewModel.getWeatherInfo()
				}
				permissions.getValue(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
					viewModel.getWeatherInfo()
				}
				else -> {

				}
			}
		}
		locationPermissionRequest?.launch(
			arrayOf(
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION
			)
		)

		binding.changeType.setOnCheckedChangeListener { _, isChecked ->
			if (isChecked) {
				val celsius = viewModel.weatherTemp.value?.minus(273) ?: 0.0
				viewModel.onWeatherTemp(celsius)
				binding.temp.text = resources.getString(R.string.temp_c, celsius)
			} else {
				val fahrenheit = viewModel.weatherTemp.value?.plus(273) ?: 0.0
				viewModel.onWeatherTemp(fahrenheit)
				binding.temp.text = resources.getString(R.string.temp_f, fahrenheit)
			}
		}
	}

	override fun onViewClick() {
		binding.icon.setOnClickListener {
			viewModel.getWeatherInfo()
		}
	}

	override fun onEach() {
		onEach(viewModel.weatherInfo) {
			with(binding) {
				it?.let {
					name.text = resources.getString(R.string.location_name, it.name)
					lat.text = resources.getString(R.string.location_lat, it.lat)
					lon.text = resources.getString(R.string.location_lon, it.lon)
					temp.text = resources.getString(R.string.temp_f, it.temp)
					humidity.text = resources.getString(R.string.humidity, it.humidity)
				}
			}
		}
		onEach(viewModel.weatherTemp) {
			with(binding) {
				it?.let {
					temp.text = resources.getString(R.string.temp_f, it)
					changeType.visible()
				}
			}
		}

		onEach(viewModel.progress) {
			if (it) {
				binding.loading.visible()
			} else {
				binding.loading.gone()
			}
		}
	}

}