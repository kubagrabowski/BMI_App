package com.example.bmi

import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val actionbar = supportActionBar
        actionbar!!.title=getString(R.string.bmi_about_Title)
        actionbar.run {
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext,R.color.claret)))
            setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun wyswietlWiecej(view:View){
        aboutmetxtplus.text=getString(R.string.bmi_info_wersja)
    }

}
