package com.example.medicalfacilitiescopy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.database.FirebaseDatabase

class HospitalRegistration : AppCompatActivity() {

    private lateinit var nameEditText: EditText
   // private lateinit var addressEditText: AutocompleteSupportFragment
    private lateinit var facilitiesCheckBoxes: List<CheckBox>

    private var selectedPlace: Place? = null


    private val database = FirebaseDatabase.getInstance().getReference("hospitals")

    private lateinit var placesClient: PlacesClient
    private lateinit var addressEditText: AutocompleteSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_registration)

        nameEditText = findViewById(R.id.nameEditText)


        facilitiesCheckBoxes = listOf(
            findViewById(R.id.mri),
            findViewById(R.id.xray),
            findViewById(R.id.ctscan),
            findViewById(R.id.ultrasound),
            findViewById(R.id.bloodtest)
        )

        val registerHospitalButton: Button =findViewById(R.id.saveButton)

        val apiKey="your_api_key

        // Initialize Places SDK
        Places.initialize(applicationContext,apiKey)
        placesClient= Places.createClient(this)

        // Create a new instance of AutocompleteSupportFragment
        addressEditText = AutocompleteSupportFragment()
        supportFragmentManager.beginTransaction().replace(R.id.addressEditText, addressEditText).commit()

        // Set the type of places to search for
        addressEditText.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG))

        // Set up a listener to handle the autocomplete results
        addressEditText.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Handle the selected place
                Toast.makeText(applicationContext, "Place: " + place.name + ", " + place.id + ", " + place.address + ", " + place.latLng, Toast.LENGTH_LONG).show()
                selectedPlace = place
                addressEditText.setText(place.address)

            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                // Handle the error
                Toast.makeText(applicationContext, "$status", Toast.LENGTH_LONG).show()
            }
        })


        findViewById<Button>(R.id.saveButton).setOnClickListener {
            val place = selectedPlace
            if (place != null) {
                saveHospital(place)
                val intent= Intent(this, MainScreen::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select a valid hospital address 1", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun saveHospital(place: Place) {
        val name = nameEditText.text.toString().trim()
        val place = selectedPlace
        if (place != null) {
            val address = place.address!!.toString().trim()
            val facilities = facilitiesCheckBoxes.filter { it.isChecked }.map { it.text.toString() }
            val lat = place.latLng!!.latitude
            val lng = place.latLng!!.longitude

            val hospital = Hospital(
                name,
                address,
                facilities,
                lat,
                lng
            )

            database.push().setValue(hospital)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Hospital saved successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error saving hospital: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please select a valid hospital address", Toast.LENGTH_SHORT).show()
        }
    }

}