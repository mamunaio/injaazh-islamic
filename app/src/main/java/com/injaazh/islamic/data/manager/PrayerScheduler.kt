package com.injaazh.islamic.data.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.injaazh.islamic.data.receiver.AdhanReceiver
import com.injaazh.islamic.domain.repository.PrayerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrayerScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val prayerRepository: PrayerRepository
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    suspend fun scheduleNextAdhan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            // Can't schedule exact alarms yet
            return
        }

        val prayerTimes = prayerRepository.getPrayerTimes().first()
        val now = Date()

        val nextPrayer = listOfNotNull(
            prayerTimes.fajr,
            prayerTimes.dhuhr,
            prayerTimes.asr,
            prayerTimes.maghrib,
            prayerTimes.isha
        ).filter { it.time.after(now) }
            .minByOrNull { it.time }

        nextPrayer?.let { prayer ->
            val intent = Intent(context, AdhanReceiver::class.java).apply {
                putExtra("PRAYER_NAME", prayer.name)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                prayer.name.hashCode(), // Unique ID for each prayer type
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    prayer.time.time,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    prayer.time.time,
                    pendingIntent
                )
            }
        }
    }
}
