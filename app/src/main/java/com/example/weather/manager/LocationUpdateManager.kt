package com.example.weather.manager

import android.*
import android.content.*
import android.content.pm.*
import android.location.*
import android.os.*
import android.provider.*
import android.util.*
import androidx.appcompat.app.*
import androidx.core.app.*
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest

class LocationUpdateManager(val context: Context) {

	private lateinit var fusedLocationClient: FusedLocationProviderClient
	private lateinit var locationRequest: LocationRequest
	private lateinit var locationCallback: LocationCallback
	var coordinatesData: ((Double, Double) -> Unit) = { lat, lon -> }

	fun checkLocation() {
		val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			showAlertLocation()
		}

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
		getLocationUpdates()
		startLocationUpdates()
	}

	private fun showAlertLocation() {
		val dialog = AlertDialog.Builder(context)
		dialog.setMessage("Your location settings is set to Off, Please enable location to use this application")
		dialog.setPositiveButton("Settings") { _, _ ->
			val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
			context.startActivity(myIntent)
		}
		dialog.setNegativeButton("Cancel") { _, _ ->
			checkLocation()
		}
		dialog.setCancelable(false)
		dialog.show()
	}

	private fun getLocationUpdates() {
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
		locationRequest = LocationRequest
			.create().apply {
				interval = 50000
				fastestInterval = 50000
				smallestDisplacement = 10f
				priority = LocationRequest.PRIORITY_HIGH_ACCURACY
			}
		locationCallback = object : LocationCallback() {
			override fun onLocationResult(locationResult: LocationResult) {
				if (locationResult.locations.isNotEmpty()) {
					val location = locationResult.lastLocation
					Log.e("location", location.toString())
					locationResult.lastLocation.latitude
					locationResult.lastLocation.longitude
					coordinatesData.invoke(
						locationResult.lastLocation.latitude,
						locationResult.lastLocation.longitude
					)
					fusedLocationClient.removeLocationUpdates(locationCallback)
				}
			}
		}
	}

	// Start location updates
	private fun startLocationUpdates() {
		if (ActivityCompat.checkSelfPermission(
				context,
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				context,
				Manifest.permission.ACCESS_COARSE_LOCATION
			) != PackageManager.PERMISSION_GRANTED
		) {
			return
		}
		fusedLocationClient.requestLocationUpdates(
			locationRequest,
			locationCallback,
			Looper.getMainLooper()
		)

	}




}