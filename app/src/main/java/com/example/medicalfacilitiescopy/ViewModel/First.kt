package com.example.medicalfacilitiescopy.ViewModel

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.medicalfacilitiescopy.R

class First : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_first, container, false)
       val userLoginButton=view.findViewById<Button>(R.id.userLoginButton)
            .setOnClickListener {
                val intent = Intent(activity, MainScreen::class.java)
                startActivity(intent)
            }


        // Inflate the layout for this fragment
        return view

    }

    }