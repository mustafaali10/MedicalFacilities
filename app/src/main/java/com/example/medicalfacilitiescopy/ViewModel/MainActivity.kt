package com.example.medicalfacilitiescopy.ViewModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.medicalfacilitiescopy.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button =findViewById(R.id.btn_getStarted)

        startButton.setOnClickListener {

            val intent= Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
    }
}