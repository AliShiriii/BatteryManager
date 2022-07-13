package com.example.batterymanager.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.batterymanager.databinding.ActivityMainBinding
import com.example.batterymanager.model.BatteryModel
import com.example.batterymanager.utils.BatteryUsage
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        val batteryPercentArray: MutableList<BatteryModel> = ArrayList()

        val batteryUsage = BatteryUsage(this)

        for (item in batteryUsage.getUsageStateList()) {
            Log.e("BatteryManager:", item.packageName + " : " + item.totalTimeInForeground)

            if (item.totalTimeInForeground > 0) {

                val batteryModel = BatteryModel()
                batteryModel.packageName = item.packageName
                batteryModel.percentUsage =
                    (item.totalTimeInForeground.toFloat() / BatteryUsage(this).getTotalTime()
                        .toFloat() * 100).toInt()

                batteryPercentArray += batteryModel
            }
        }

        var sortedList = batteryPercentArray.groupBy { it.packageName }
            .mapValues { entry -> entry.value.sumBy { it.percentUsage } }.toList()
            .sortedWith(compareBy { it.second }).reversed()

        for (item in sortedList) {

            val timePerApp =
                item.second.toFloat() / 100 * batteryUsage.getTotalTime().toFloat() / 1000 / 60
            val hour = timePerApp / 60
            val min = timePerApp % 60

            Log.e(
                "BatteryManager: ",
                "${item.first} : ${item.second} time usage is : ${hour.roundToInt()} : ${min.roundToInt()}"
            )

        }

        registerReceiver(batteryInfoBroadcastReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    }

    private var batteryInfoBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            var batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

            if (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) == 0) {
                mainBinding.txtPlug.text = "plug-out"

            } else {

                mainBinding.txtPlug.text = "plug-in"
            }

            mainBinding.txtTemperature.text =
                (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10).toString() + " °C"
            mainBinding.txtVoltage.text =
                (intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) / 1000).toString() + " volt"
            mainBinding.txtTechnology.text = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)

            mainBinding.circularProgressBar.progressMax = 100f
            mainBinding.circularProgressBar.setProgressWithAnimation(batteryLevel.toFloat())

            mainBinding.txtCharge.text = batteryLevel.toString()
        }


    }

}