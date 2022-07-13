package com.example.batterymanager

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.*

class BatteryUsage {

    private fun getUsageStateList(context: Context): List<UsageStats> {
        val usageStateManagement = getUsageStateManager(context)
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.YEAR, -1)
        val startTime = calendar.timeInMillis
        return usageStateManagement.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)

    }

    private fun getUsageStateManager(context: Context): UsageStatsManager {

        return context.getSystemService("usageState") as UsageStatsManager

    }
}