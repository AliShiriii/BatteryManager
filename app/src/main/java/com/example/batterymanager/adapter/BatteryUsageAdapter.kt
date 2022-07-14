package com.example.batterymanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.batterymanager.R
import com.example.batterymanager.model.BatteryModel
import kotlin.math.roundToInt

class BatteryUsageAdapter(
    private val battery: MutableList<BatteryModel>,
    private val totalTime: Long
) : RecyclerView.Adapter<BatteryUsageAdapter.BatteryViewHolder>() {

    private var batteryFinalList: MutableList<BatteryModel> = ArrayList()

    init {

        batteryFinalList = calcBatteryUsage(battery)

    }

    class BatteryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var test: TextView = view.findViewById(R.id.test)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatteryViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val listItem = layoutInflater.inflate(R.layout.item_battery_usage, parent, false)

        return BatteryViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: BatteryViewHolder, position: Int) {

        holder.test.text = "${batteryFinalList[position].packageName} : ${batteryFinalList[position].percentUsage} : ${batteryFinalList[position].timeUsage}"

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
            batteryModel.timeUsage = "${hour.roundToInt()} : ${min.roundToInt()}"

            finalList += batteryModel

        }
        return finalList
    }

}