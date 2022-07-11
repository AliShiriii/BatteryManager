package com.example.batterymanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(batteryInfoBroadcastReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    }

    private var batteryInfoBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            var batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            var batteryPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
            var batteryTemp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10
            var batteryVol = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) / 1000
            Log.e("1010", batteryLevel.toString())
            Log.e("1010", batteryPlugged.toString())
            Log.e("1010", batteryTemp.toString())
            Log.e("1010", batteryVol.toString())
        }

    }

}