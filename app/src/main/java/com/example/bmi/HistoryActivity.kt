package com.example.bmi

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    var pref: SharedPreferences? = null

    var historia:ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val actionbar = supportActionBar
        actionbar!!.title=getString(R.string.bmi_history_Title)
        actionbar.run {
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext,R.color.claret)))
            setDisplayHomeAsUpEnabled(true)
        }

        pref = this.getSharedPreferences("history_bmi", Context.MODE_PRIVATE)

        //
        createHistory()
        //

        MyHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
        MyHistoryRecyclerView.adapter = MyAdapter(historia,this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    fun createHistory() {

        val start = pref!!.getInt("START",0)
        var ile = 0

        while(ile<10){
            val x:String?=(pref!!.getString("HIS"+((start-1-ile+10)%10).toString(),"-"))
            x?.let{historia.add(x)
            }
            ile++
        }

    }

}




