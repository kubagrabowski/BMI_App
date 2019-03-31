package com.example.bmi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.example.bmi.logic.BmiForKgCm
import com.example.bmi.logic.BmiForPoundsInchs
import java.text.DateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var swaped = 0
    var aktmasa = 1
    var aktheight = 1

    var pref: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val actionbar = supportActionBar

        actionbar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext,R.color.claret)))


        pref = this.getSharedPreferences("history_bmi", Context.MODE_PRIVATE)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
            when (item!!.itemId){

                R.id.about -> {
                    startActivity(Intent(this,AboutActivity::class.java))
                    return true
                }

                R.id.swap -> {
                    when(swaped){
                        0 -> {
                            swaped=1
                            policzbmi.setOnClickListener{ v -> wyliczMnieToPoundInch(v) }
                            masa.text=getString(R.string.bmi_main_mass_lb)
                            wzrost.text=getString(R.string.bmi_main_height_in)

                            wylbmi.text =""
                            dodatkowy.text=""
                            editmasa.text.clear()
                            editwzrost.text.clear()

                            Toast.makeText(this,getString(R.string.bmi_main_about_swapped), Toast.LENGTH_SHORT).show()
                            return true
                        }
                        1 -> {
                            swaped=0
                            policzbmi.setOnClickListener{ v -> wyliczMnieTo(v) }
                            masa.text=getString(R.string.bmi_main_mass_kg)
                            wzrost.text=getString(R.string.bmi_main_height_cm)

                            wylbmi.text =""
                            dodatkowy.text=""
                            editmasa.text.clear()
                            editwzrost.text.clear()

                            Toast.makeText(this,getString(R.string.bmi_main_about_swapped), Toast.LENGTH_SHORT).show()
                            return true
                        }
                        else -> {
                            return true
                        }
                    }
                }
                R.id.history ->{
                    startActivity(Intent(this,HistoryActivity::class.java))
                    return true
                }

                else ->{
                    return super.onOptionsItemSelected(item)
                }

            }



    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putCharSequence("BMI",wylbmi.text)
        outState?.putCharSequence("DODATKOWY",dodatkowy.text)
        outState?.putInt("SWAPED",swaped)
        outState?.putInt("AKT_M",aktmasa)
        outState?.putInt("AKT_H",aktheight)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        wylbmi.text= savedInstanceState?.getCharSequence("BMI")
        dodatkowy.text = savedInstanceState?.getCharSequence("DODATKOWY")
        aktmasa = savedInstanceState?.getInt("AKT_M",1)!!
        aktheight = savedInstanceState.getInt("AKT_H",1)
        swaped = savedInstanceState.getInt("SWAPED")
        when(swaped){
            1 -> {
                policzbmi.setOnClickListener{ v -> wyliczMnieToPoundInch(v) }
                masa.text=getString(R.string.bmi_main_mass_lb)
                wzrost.text=getString(R.string.bmi_main_height_in)

            }
        }
        if(wylbmi.text.isNotEmpty()) {
            dodatkoweinfo(wylbmi.text.toString().replace(",", ".").toDouble(),0)
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    fun info(view:View){
        if(wylbmi.text.isNotEmpty()){

            val intent = Intent(this,InfoActivity::class.java)

            intent.putExtra("BMI",wylbmi.text)
            intent.putExtra("SWAPED",swaped)
            intent.putExtra("MASSINT",aktmasa)
            intent.putExtra("HEIGHTINT", aktheight)

            startActivity(intent)


        }
        else{
            Toast.makeText(this,getString(R.string.bmi_main_brakbmi),Toast.LENGTH_SHORT).show()
        }
    }

    fun pobierzWage():Int{
        if(editmasa.text.isNotEmpty()) {

            var limit = 1000
            if(swaped==1){
                limit = 2200
            }


            val kg =  try { editmasa.text.toString().toInt() }
            catch(exp:NumberFormatException){ 0 }
            if(kg!=0 && kg<=limit) {
                return kg
            }
            else{
                editmasa.text.clear()
                editmasa.error = getString(R.string.bmi_main_ograniczeniewaga) + limit.toString()
            }
        }
        else{
            editmasa.error = getString(R.string.bmi_main_brakwagi)
        }
        return 0
    }

    fun pobierzWzrost():Int{

        if(editwzrost.text.isNotEmpty()) {

            var limit = 300
            if(swaped==1){
                limit = 120
            }

            val height = try{editwzrost.text.toString().toInt()}
            catch(exp:java.lang.NumberFormatException){ 0 }
            if(height!=0 && height <= limit) {
                return height
            }
            else{
                editwzrost.text.clear()
                editwzrost.error = getString(R.string.bmi_main_ograniczeniewzrost)+limit.toString()
            }
        }
        else{
            editwzrost.error = getString(R.string.bmi_main_brakwzrostu)
        }
        return 0
    }

    fun wyliczMnieTo(view : View){

        dodatkoweinfo(0.0,0)

            val kg = pobierzWage()
            val height = pobierzWzrost()
            if(kg>0 && height >0) {
                val bmi = BmiForKgCm(kg, height)
                aktmasa = kg
                aktheight = height
                val topr:String = String.format("%.2f",bmi.countBmi())

                wylbmi.text = topr

                val start = pref!!.getInt("START",0)
                dodatkoweinfo(bmi.countBmi(),start)

                val aktData = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time)



                val topref ="Data" + " " + aktData + "\n" +  getString(R.string.bmi_main_mass_kg)+ " " + kg.toString()+ " " + getString(R.string.bmi_main_height_cm)+ " " + height.toString()+ "\n" + getString(R.string.bmi_main_BMI)+ " " + topr


                pref!!.edit().putString("HIS"+(start).toString(),topref).apply()
                pref!!.edit().putInt("START",(start+1)%10).apply()


            }
            else{
                wylbmi.text = ""
            }


    }

    fun wyliczMnieToPoundInch(view : View){

        dodatkoweinfo(0.0,0)

        val kg = pobierzWage()
        val height = pobierzWzrost()
        if(kg>0 && height >0) {
            val bmi = BmiForPoundsInchs(kg, height)
            aktmasa = kg
            aktheight = height
            val topr:String = String.format("%.2f",bmi.countBmi())
            wylbmi.text =topr

            val start = pref!!.getInt("START",0)
            dodatkoweinfo(bmi.countBmi(),start)

            val aktData = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time)


            val topref = "Data" + " " + aktData + "\n" + getString(R.string.bmi_main_mass_lb) + " " + kg.toString()+ " " + getString(R.string.bmi_main_height_in) + " " + height.toString()+ "\n" + getString(R.string.bmi_main_BMI) + " " + topr


            pref!!.edit().putString("HIS"+(start).toString(),topref).apply()
            pref!!.edit().putInt("START",(start+1)%10).apply()
        }
        else{
            wylbmi.text = ""
        }


    }

    fun wprowadzam(view:View){
        editmasa.error = null
        editwzrost.error = null
    }

    fun dodatkoweinfo(bmi:Double,start:Int){

        when(bmi){
            0.0 -> {
                dodatkowy.text=""
            }
            in 0.0..16.0 ->{
                dodatkowy.text=getString(R.string.bmi_wyglodzenie)
                wylbmi.setTextColor(ContextCompat.getColor(applicationContext,R.color.st_tropaz))
                dodatkowy.setTextColor(ContextCompat.getColor(applicationContext, R.color.st_tropaz))
                pref!!.edit().putInt("COL"+start.toString(),1).apply()
            }
            in 16.0..17.0 ->{
                dodatkowy.text=getString(R.string.bmi_wychudzenie)
                wylbmi.setTextColor(ContextCompat.getColor(applicationContext,R.color.st_tropaz))
                dodatkowy.setTextColor(ContextCompat.getColor(applicationContext, R.color.st_tropaz))
                pref!!.edit().putInt("COL"+start.toString(),1).apply()
            }
            in 17.0..18.5 ->{
                dodatkowy.text=getString(R.string.bmi_niedowaga)
                wylbmi.setTextColor(ContextCompat.getColor(applicationContext,R.color.verdigris))
                dodatkowy.setTextColor(ContextCompat.getColor(applicationContext, R.color.verdigris))
                pref!!.edit().putInt("COL"+start.toString(),2).apply()
            }
            in 18.5..25.5 ->{
                dodatkowy.text=getString(R.string.bmi_norma)
                wylbmi.setTextColor(ContextCompat.getColor(applicationContext,R.color.trinidad))
                dodatkowy.setTextColor(ContextCompat.getColor(applicationContext, R.color.trinidad))
                pref!!.edit().putInt("COL"+start.toString(),3).apply()
            }
            in 25.5..30.0 ->{
                dodatkowy.text=getString(R.string.bmi_nadwaga)
                wylbmi.setTextColor(ContextCompat.getColor(applicationContext,R.color.guardsman_red))
                dodatkowy.setTextColor(ContextCompat.getColor(applicationContext, R.color.guardsman_red))
                pref!!.edit().putInt("COL"+start.toString(),4).apply()
            }
            in 30.0..35.0 ->{
                dodatkowy.text=getString(R.string.bmi_IstOtyl)
                wylbmi.setTextColor(ContextCompat.getColor(applicationContext,R.color.guardsman_red))
                dodatkowy.setTextColor(ContextCompat.getColor(applicationContext, R.color.guardsman_red))
                pref!!.edit().putInt("COL"+start.toString(),4).apply()
            }
            in 35.0..40.0 ->{
                dodatkowy.text=getString(R.string.bmi_IIstOtyl)
                wylbmi.setTextColor(ContextCompat.getColor(applicationContext,R.color.amethyst))
                dodatkowy.setTextColor(ContextCompat.getColor(applicationContext, R.color.amethyst))
                pref!!.edit().putInt("COL"+start.toString(),5).apply()
            }
            else -> {
                dodatkowy.text=getString(R.string.bmi_IIIstOtyl)
                wylbmi.setTextColor(ContextCompat.getColor(applicationContext,R.color.amethyst))
                dodatkowy.setTextColor(ContextCompat.getColor(applicationContext, R.color.amethyst))
                pref!!.edit().putInt("COL"+start.toString(),5).apply()
            }
        }


    }

}
