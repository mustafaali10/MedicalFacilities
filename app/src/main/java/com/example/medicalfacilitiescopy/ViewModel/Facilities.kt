package com.example.medicalfacilitiescopy.ViewModel

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalfacilitiescopy.Adapter.ItemsAdapter
import com.example.medicalfacilitiescopy.Model.ItemsData
import com.example.medicalfacilitiescopy.R


class Facilities : Fragment() {


    private var mList=ArrayList<ItemsData>()
    private lateinit var adapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_facilities, container, false)

        val recyclerView:RecyclerView=view.findViewById(R.id.recyclerView) //POSSIBLE ERROR HERE

        recyclerView.setHasFixedSize(true)
        adapter= ItemsAdapter(mList)
        recyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false) //POSSIBLE ERROR HERE ALSO

        addDataToList()



        recyclerView.adapter=adapter

        adapter.setOnItemClickListener(object : ItemsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

                val intent= Intent(context, DetailsActivity::class.java)

                intent.putExtra("title",mList[position].title)
                intent.putExtra("logo",mList[position].logo)
                intent.putExtra("price",mList[position].price)
                startActivity(intent)

                Toast.makeText(context,"Item $position clicked",Toast.LENGTH_SHORT).show()
            }

        })

        return view
    }

    private fun addDataToList(){

        mList.add(ItemsData("M.R.I", R.drawable.mrimachine,"₹ 5200"))
        mList.add(ItemsData("X-Ray", R.drawable.xray,"₹ 800"))
        mList.add(ItemsData("Blood Test", R.drawable.bloodtest,"₹ 500"))
        mList.add(ItemsData("C.T Scan", R.drawable.ctscan,"₹ 4000"))
        mList.add(ItemsData("Ultrasound", R.drawable.ultrasound,"₹ 5400"))


    }

}