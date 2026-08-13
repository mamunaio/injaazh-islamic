package com.injaazh.islamic.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.injaazh.islamic.data.manager.PrayerScheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var prayerScheduler: PrayerScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
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
}
