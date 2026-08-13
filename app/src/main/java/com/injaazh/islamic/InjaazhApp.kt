package com.injaazh.islamic

import android.app.Application
import com.injaazh.islamic.util.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InjaazhApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannel(this)
    }
}
