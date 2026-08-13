package com.injaazh.islamic.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.injaazh.islamic.data.manager.PrayerScheduler
import com.injaazh.islamic.util.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdhanReceiver : BroadcastReceiver() {

    @Inject
    lateinit var prayerScheduler: PrayerScheduler

    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra("PRAYER_NAME") ?: "নামাজ"
        
        // Show Notification
        NotificationHelper.showAdhanNotification(
            context,
            "নামাজের সময় হয়েছে",
            "$prayerName এর সময় হয়েছে। নামাজ পড়ুন।"
        )

        // Schedule next one
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                prayerScheduler.scheduleNextAdhan()
            } finally {
                pendingResult.finish()
            }
        }
    }
}
