package com.example.bmi

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(val elem : ArrayList<String>, val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    var pref: SharedPreferences? = context.getSharedPreferences("history_bmi", Context.MODE_PRIVATE)

    class ViewHolder(private val view:View) : RecyclerView.ViewHolder(view){
        val tvtxtlist = view.txt_list
    }


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return elem.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        return MyAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvtxtlist?.text = elem[position]


        val start = pref!!.getInt("START",0)
        when(pref!!.getInt("COL"+((start-1-position+10)%10).toString(),0)){
            1 ->{
                holder.tvtxtlist?.setTextColor(ContextCompat.getColor(context,R.color.st_tropaz))
            }
            2 -> {
                holder.tvtxtlist?.setTextColor(ContextCompat.getColor(context,R.color.verdigris))
            }
            3 ->{
                holder.tvtxtlist?.setTextColor(ContextCompat.getColor(context,R.color.trinidad))
            }
            4 -> {
                holder.tvtxtlist?.setTextColor(ContextCompat.getColor(context,R.color.guardsman_red))
            }
            5 ->{
                holder.tvtxtlist?.setTextColor(ContextCompat.getColor(context,R.color.amethyst))
            }
            else ->{
                holder.tvtxtlist?.setTextColor(ContextCompat.getColor(context,R.color.gamboge))
            }
        }

    }

}