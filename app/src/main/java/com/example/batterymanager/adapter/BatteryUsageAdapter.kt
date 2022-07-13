package com.example.batterymanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.batterymanager.R
import com.example.batterymanager.model.BatteryModel

class BatteryUsageAdapter(private val battery: MutableList<BatteryModel>): RecyclerView.Adapter<BatteryUsageAdapter.BatteryViewHolder>() {

    class BatteryViewHolder(view: View): RecyclerView.ViewHolder(view){

        var test : TextView = view.findViewById(R.id.test)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatteryViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val listItem = layoutInflater.inflate(R.layout.item_battery_usage, parent, false)

        return BatteryViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: BatteryViewHolder, position: Int) {

        holder.test.text = "${battery[position].packageName} : ${battery[position].percentUsage}"

    }

    override fun getItemCount(): Int {

        return battery.size
    }

}