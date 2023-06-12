package com.example.medicalfacilitiescopy.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalfacilitiescopy.Model.ItemsData
import com.example.medicalfacilitiescopy.R

class ItemsAdapter(var mList:List<ItemsData>):RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {


    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener=listener

    }

    inner class ItemViewHolder(itemView: View,listener: OnItemClickListener):RecyclerView.ViewHolder(itemView){

        val logo:ImageView=itemView.findViewById(R.id.logo)
        val titleTv:TextView=itemView.findViewById(R.id.scanText)
        val price:TextView=itemView.findViewById(R.id.price)
        init {
            itemView.setOnClickListener { listener.onItemClick(adapterPosition) }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
        return ItemViewHolder(view,mListener)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.logo.setImageResource(mList[position].logo)
        holder.titleTv.text=mList[position].title
        holder.price.text=mList[position].price
    }




}