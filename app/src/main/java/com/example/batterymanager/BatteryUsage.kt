package com.example.batterymanager

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import java.util.*

class BatteryUsage(context: Context) {

    init {

        //Check if permission enable
        if (getUsageStateList(context).isEmpty()) {

            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            context.startActivity(intent)

        }
    }

    private fun getUsageStateList(context: Context): List<UsageStats> {
        val usageStateManagement = getUsageStateManager(context)
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

    private fun getUsageStateManager(context: Context): UsageStatsManager {

        return context.getSystemService("usageState") as UsageStatsManager

    }
}