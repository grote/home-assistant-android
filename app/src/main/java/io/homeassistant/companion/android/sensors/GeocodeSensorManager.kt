package io.homeassistant.companion.android.sensors

import android.content.Context
import android.location.Geocoder
import android.util.Log
import io.homeassistant.companion.android.domain.integration.Sensor
import io.homeassistant.companion.android.domain.integration.SensorRegistration

class GeocodeSensorManager : SensorManager {

    companion object {
        private const val TAG = "GeocodeSM"
    }

    override fun getSensorRegistrations(context: Context): List<SensorRegistration<Any>> {
        val sensor = getGeocodedLocation(context)
        if (sensor != null) {
            return listOf(
                SensorRegistration<Any>(
                    sensor,
                    "Geocoded Location"
                )
            )
        }
        return emptyList()
    }

    override fun getSensors(context: Context): List<Sensor<Any>> {
        val geocodedSensor = getGeocodedLocation(context)

        if (geocodedSensor != null) {
            return listOf(geocodedSensor)
        }

        return emptyList()
    }

    private fun getGeocodedLocation(context: Context): Sensor<Any>? {
        return null
    }
}
