package com.example.batterymanager.adapter

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.batterymanager.R
import com.example.batterymanager.model.BatteryModel
import kotlin.math.roundToInt

class BatteryUsageAdapter(
    private val context: Context,
    private val battery: MutableList<BatteryModel>,
    private val totalTime: Long
) : RecyclerView.Adapter<BatteryUsageAdapter.BatteryViewHolder>() {

    private var batteryFinalList: MutableList<BatteryModel> = ArrayList()

    init {

        batteryFinalList = calcBatteryUsage(battery)

    }

    class BatteryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtPercent: TextView = view.findViewById(R.id.txtPercent)
        var txtAppName: TextView = view.findViewById(R.id.txtAppName)
        var txtTime: TextView = view.findViewById(R.id.txtTime)
        var progressBar: ProgressBar = view.findViewById(R.id.progressBar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatteryViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val listItem = layoutInflater.inflate(R.layout.item_battery_usage, parent, false)

        return BatteryViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: BatteryViewHolder, position: Int) {

        holder.txtPercent.text = batteryFinalList[position].percentUsage.toString() + "%"
        holder.txtTime.text = batteryFinalList[position].timeUsage
        holder.txtAppName.text = getAppName(batteryFinalList[position].packageName.toString())
        holder.progressBar.progress = batteryFinalList[position].percentUsage
    }

    override fun getItemCount(): Int {

        return batteryFinalList.size
    }

    fun calcBatteryUsage(batteryPercentArray: MutableList<BatteryModel>): MutableList<BatteryModel> {

        val finalList: MutableList<BatteryModel> = ArrayList()

        var sortedList = batteryPercentArray.groupBy { it.packageName }
            .mapValues { entry -> entry.value.sumBy { it.percentUsage } }.toList()
            .sortedWith(compareBy { it.second }).reversed()

        for (item in sortedList) {

            var batteryModel = BatteryModel()
            val timePerApp =
                item.second.toFloat() / 100 * totalTime.toFloat() / 1000 / 60
            val hour = timePerApp / 60
            val min = timePerApp % 60

            batteryModel.packageName = item.first
            batteryModel.percentUsage = item.second
            batteryModel.timeUsage = "${hour.roundToInt()} hour ${min.roundToInt()} minutes"

            finalList += batteryModel

        }
        return finalList
    }

    fun getAppName(packageName: String): String {

        val packageManager = context.applicationContext.packageManager

        val appInfo: ApplicationInfo? = try {

            packageManager.getApplicationInfo(packageName, 0)

        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

        return (if (appInfo != null) packageManager.getApplicationLabel(appInfo) else "unknown") as String

    }
}