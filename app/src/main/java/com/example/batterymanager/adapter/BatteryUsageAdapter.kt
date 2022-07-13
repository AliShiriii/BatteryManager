package com.example.batterymanager.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.batterymanager.model.BatteryModel

class BatteryUsageAdapter(private val battery: MutableList<BatteryModel>): RecyclerView.Adapter<BatteryUsageAdapter.BatteryHolder>() {

    class BatteryHolder(view: View): RecyclerView.ViewHolder(view){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatteryHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BatteryHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}