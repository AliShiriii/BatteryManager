package com.example.batterymanager

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import java.util.*

class BatteryUsage(context: Context) {

    private var myContext = context

    init {

        //Check if permission enable
        if (getUsageStateList().isEmpty()) {

            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            context.startActivity(intent)

        }
    }

    fun getUsageStateList(): List<UsageStats> {
        val usageStateManagement = getUsageStateManager(myContext)
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.YEAR, -1)
        val startTime = calendar.timeInMillis
        return usageStateManagement.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

    }

    private fun getTotalTime(): Long {

        var totalTime: Long = 0
        for (item in getUsageStateList()) {

            totalTime += item.totalTimeInForeground
        }
        return totalTime

    }

    private fun getUsageStateManager(context: Context): UsageStatsManager {

        return context.getSystemService("usagestats") as UsageStatsManager

    }
}