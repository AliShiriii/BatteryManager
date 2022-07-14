package com.example.batterymanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.batterymanager.R
import com.example.batterymanager.adapter.BatteryUsageAdapter
import com.example.batterymanager.databinding.ActivityUsageBatteryBinding
import com.example.batterymanager.model.BatteryModel
import com.example.batterymanager.utils.BatteryUsage
import kotlin.math.roundToInt

class UsageBatteryActivity : AppCompatActivity() {

    private lateinit var usageBattery: ActivityUsageBatteryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usageBattery = ActivityUsageBatteryBinding.inflate(layoutInflater)
        val view = usageBattery.root
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

        val adapter = BatteryUsageAdapter(this, batteryPercentArray, batteryUsage.getTotalTime())
        usageBattery.batteryRecyclerView.setHasFixedSize(true)
        usageBattery.batteryRecyclerView.layoutManager = LinearLayoutManager(this)

        usageBattery.batteryRecyclerView.adapter = adapter

    }
}