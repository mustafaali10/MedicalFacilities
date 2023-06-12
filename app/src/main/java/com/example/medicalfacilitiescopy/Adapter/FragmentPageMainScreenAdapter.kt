package com.example.medicalfacilitiescopy.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.medicalfacilitiescopy.ViewModel.Facilities
import com.example.medicalfacilitiescopy.ViewModel.Hospitals

class FragmentPageMainScreenAdapter(fragmentManager:FragmentManager,
                                    lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position==0)
            Facilities()
            else
                Hospitals()

    }


}