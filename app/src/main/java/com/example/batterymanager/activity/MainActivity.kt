package com.example.batterymanager.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.batterymanager.R
import com.example.batterymanager.databinding.ActivityMainBinding
import com.example.batterymanager.helper.SharedPreferenceManager
import com.example.batterymanager.service.BatteryAlarmService

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.imageMenu.setOnClickListener {
            mainBinding.drawer.openDrawer(Gravity.RIGHT)
        }

        mainBinding.includeDrawer.txtAppUsage.setOnClickListener {
            startActivity(Intent(this@MainActivity, UsageBatteryActivity::class.java))
            mainBinding.drawer.closeDrawer(Gravity.RIGHT)
        }

        registerReceiver(batteryInfoBroadcastReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        if (SharedPreferenceManager.isServiceOn(this@MainActivity) == true) {

            mainBinding.includeDrawer.serviceSwitchText.text = "Service is on"
            mainBinding.includeDrawer.serviceSwitch.isChecked = true
            startServices()

        } else {
            mainBinding.includeDrawer.serviceSwitchText.text = "Service is off"
            mainBinding.includeDrawer.serviceSwitch.isChecked = true
            stopServices()
        }
        mainBinding.includeDrawer.serviceSwitch.setOnCheckedChangeListener { switch, isCheck ->
            SharedPreferenceManager.setServiceState(this@MainActivity, isCheck)
            if (isCheck) {
                startServices()
                mainBinding.includeDrawer.serviceSwitchText.text = "Service is on"
                Toast.makeText(applicationContext, "service is turn on", Toast.LENGTH_LONG).show()
            } else {
                stopServices()
                mainBinding.includeDrawer.serviceSwitchText.text = "Service is off"
                Toast.makeText(applicationContext, "service is turn off", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startServices() {

        val serviceIntent = Intent(this, BatteryAlarmService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopServices() {
        val serviceIntent = Intent(this, BatteryAlarmService::class.java)

        stopService(serviceIntent)


    }

    private var batteryInfoBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {

            val batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

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

            when (intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)) {

                BatteryManager.BATTERY_HEALTH_DEAD -> {
                    mainBinding.txtHealth.text =
                        "your battery is fully dead, please change your battery"
                    mainBinding.txtHealth.setTextColor(Color.parseColor("#000000"))
                    mainBinding.imageHealth.setImageResource(R.drawable.health_dead)

                }
                BatteryManager.BATTERY_HEALTH_GOOD -> {
                    mainBinding.txtHealth.text =
                        "your battery is good, please take care of that "
                    mainBinding.txtHealth.setTextColor(Color.GREEN)
                    mainBinding.imageHealth.setImageResource(R.drawable.health_good)

                }
                BatteryManager.BATTERY_HEALTH_COLD -> {
                    mainBinding.txtHealth.text = "your battery is cold, it's ok"
                    mainBinding.txtHealth.setTextColor(Color.BLUE)
                    mainBinding.imageHealth.setImageResource(R.drawable.health_cold)

                }
                BatteryManager.BATTERY_HEALTH_OVERHEAT -> {
                    mainBinding.txtHealth.text =
                        "your battery is overHeat, please don't work with your phone "
                    mainBinding.txtHealth.setTextColor(Color.RED)
                    mainBinding.imageHealth.setImageResource(R.drawable.health_overheat)
                }
                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> {
                    mainBinding.txtHealth.text =
                        "your battery is fully dead, please change your battery"
                    mainBinding.txtHealth.setTextColor(Color.YELLOW)
                    mainBinding.imageHealth.setImageResource(R.drawable.health_volt)
                }
                else -> {
                    mainBinding.txtHealth.text =
                        "your battery is fully dead, please change your battery"
                    mainBinding.txtHealth.setTextColor(Color.parseColor("#000000"))
                    mainBinding.imageHealth.setImageResource(R.drawable.health_dead)

                }
            }

        }
    }

}