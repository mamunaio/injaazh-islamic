package com.injaazh.islamic.data.repository

import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import com.injaazh.islamic.data.manager.LocationHelper
import com.injaazh.islamic.domain.model.PrayerTime
import com.injaazh.islamic.domain.model.PrayerTimesState
import com.injaazh.islamic.domain.repository.PrayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class PrayerRepositoryImpl @Inject constructor(
    private val locationHelper: LocationHelper
) : PrayerRepository {

    override fun getPrayerTimes(): Flow<PrayerTimesState> = flow {
        // Dedicated GPS location detection
        val location = locationHelper.getCurrentLocation()
        
        val coordinates = if (location != null) {
            Coordinates(location.latitude, location.longitude)
        } else {
            Coordinates(23.8103, 90.4125) // Default to Dhaka
        }

        val cityName = if (location != null) {
            locationHelper.getCityName(location.latitude, location.longitude)
        } else {
            "ঢাকা (ডিফল্ট)"
        }

        val dateComponents = DateComponents.from(Date())
        val params = CalculationMethod.KARACHI.parameters
        val adhanTimes = PrayerTimes(coordinates, dateComponents, params)

        val state = PrayerTimesState(
            fajr = PrayerTime("ফজর", adhanTimes.fajr),
            sunrise = PrayerTime("সূর্যোদয়", adhanTimes.sunrise),
            dhuhr = PrayerTime("যোহর", adhanTimes.dhuhr),
            asr = PrayerTime("আসর", adhanTimes.asr),
            maghrib = PrayerTime("মাগরিব", adhanTimes.maghrib),
            isha = PrayerTime("এশা", adhanTimes.isha),
            locationName = cityName
        )
        emit(state)
    }
}
