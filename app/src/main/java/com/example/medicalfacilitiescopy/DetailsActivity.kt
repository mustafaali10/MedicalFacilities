package com.example.medicalfacilitiescopy

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class DetailsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        recyclerView=findViewById(R.id.recyclerView2)
        val searchView=findViewById<SearchView>(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(this)


        // Create a FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        requestLocationPermission()


    }

    private fun requestLocationPermission() {

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST_CODE)
        }

        else{
            retrieveLocation()
        }



    }

    private fun retrieveLocation() {


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

            if (location !=null){
                val userLatitude=location.latitude
                val userLongitude=location.longitude

                queryNearestHospitals(userLatitude,userLongitude)

            }

            else{
                Toast.makeText(this,"No location found",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun queryNearestHospitals(userLatitude: Double, userLongitude: Double) {

        val hospitalRef=FirebaseDatabase.getInstance().getReference("hospitals")
        hospitalRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val hospitals = mutableListOf<Hospital>()

                for(hospitalSnapshot in snapshot.children){

                    val name = hospitalSnapshot.child("name").value as String
                    val address = hospitalSnapshot.child("address").value as String
                    val latitude = hospitalSnapshot.child("latitude").value as Double
                    val longitude = hospitalSnapshot.child("longitude").value as Double
                    val facilities = hospitalSnapshot.child("facilities").value as List<String>
                    val hospital = Hospital(name, address, facilities, latitude, longitude)
                    hospitals.add(hospital)
                }

                val nearestHospitals= hospitals.filter{it.facilities.contains("MRI")}
                val sortedHospitals=nearestHospitals.sortedBy { calculateDistance(it.latitude,it.longitude,userLatitude,userLongitude) }
                populateResults(sortedHospitals,userLatitude,userLongitude)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailsActivity, "Error in showing results", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun populateResults(hospitals: List<Hospital>,userLatitude: Double,userLongitude: Double){
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val intent=intent
        val title=intent.getStringExtra("title")
        val price=intent.getStringExtra("price")
        val logo=intent.getIntExtra("logo",0)
        var adapter=HospitalListAdapter(hospitals,userLatitude,userLongitude,title!!,price!!,logo)
        recyclerView.adapter=adapter

    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {

        val radius = 6371 // Radius of the Earth in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return radius * c

    }

    override fun onRequestPermissionsResult(
        requestCode:Int,
        permissions: Array<out String>,
        grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                retrieveLocation()
            }
            else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
            }

        }
    }
}