package com.example.medicalfacilitiescopy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class HospitalListAdapter (private val hospitals:List<Hospital>,
                           private val userLatitude:Double,
                           private val userLongitude:Double,
                           private val scanName:String,
                           private val scanPrice:String,
                           private val scanLogo:Int):
RecyclerView.Adapter<HospitalListAdapter.HospitalViewHolder>(){

    inner class HospitalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val hospitalName: TextView = itemView.findViewById(R.id.hospitalName)
        val hospitalAddress: TextView = itemView.findViewById(R.id.hospitalAddress)
        val hospitalDistance: TextView = itemView.findViewById(R.id.hospitalDistance)
        val scanName: TextView = itemView.findViewById(R.id.scanName)
        val scanPrice: TextView = itemView.findViewById(R.id.scanPrice)
        val scanImage: ImageView = itemView.findViewById(R.id.scanImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.each_hospital,parent,false)
        return HospitalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hospitals.size
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val hospital=hospitals[position]
        holder.hospitalName.text=hospital.name
        holder.hospitalAddress.text=hospital.address
        holder.hospitalDistance.text="Distance: ${calculateDistance(
            hospital.latitude,hospital.longitude,userLatitude,userLongitude)} kms"
        holder.scanName.text=scanName
        holder.scanPrice.text=scanPrice
        holder.scanImage.setImageResource(scanLogo)

    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {

        val radius = 6371 // Radius of the Earth in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val exactDistance = (radius * c)
        val decimalFormat = java.text.DecimalFormat("#.00")
        val formattedDistance = decimalFormat.format(exactDistance)
        return formattedDistance.toDouble()

    }
}