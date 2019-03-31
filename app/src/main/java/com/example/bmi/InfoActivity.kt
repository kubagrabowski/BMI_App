package com.example.bmi


import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.bmi.logic.BmiForKgCm
import com.example.bmi.logic.BmiForPoundsInchs
import kotlinx.android.synthetic.main.activity_info.*


class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.bmi_info_Title)
        actionbar.run {
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext,R.color.claret)))
            setDisplayHomeAsUpEnabled(true)
        }

        val masa = intent.getIntExtra("MASSINT",0).toString()
        val wzrost = intent.getIntExtra("HEIGHTINT",0).toString()
        val bmi = intent.getCharSequenceExtra("BMI")


        when(intent.getIntExtra("SWAPED",0)){
            0 -> {
                zpaki.text = getString(R.string.bmi_main_mass_kg) + masa +"\n"+ getString(R.string.bmi_main_height_cm) + wzrost + "\n"+getString(R.string.bmi_main_BMI) + bmi
            }
            1 -> {
                zpaki.text = getString(R.string.bmi_main_mass_lb) + masa +"\n"+ getString(R.string.bmi_main_height_in) + wzrost + "\n"+getString(R.string.bmi_main_BMI) + bmi
            }
        }





    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun loremIpsum(view:View){
        val swap=intent.getIntExtra("SWAPED",0)
        val kg = intent.getIntExtra("MASSINT",0)
        val h = intent.getIntExtra("HEIGHTINT",0)


        when(swap){
            0 ->{
                val bmi = BmiForKgCm(kg,h)
                ustawLI(bmi.countBmi())
            }
            1 ->{
                val bmi = BmiForPoundsInchs(kg,h)
                ustawLI(bmi.countBmi())
            }
        }



        }

    fun ustawLI(bmi:Double){

        when(bmi){
            in 0.0..16.0 ->{
                LItxt.text=(getString(R.string.bmi_wyglodzenie)+getString(R.string.lorem_ipsum))
                LItxt.setTextColor(ContextCompat.getColor(applicationContext,R.color.st_tropaz))
            }
            in 16.0..17.0 ->{
                LItxt.text=getString(R.string.bmi_wychudzenie)+getString(R.string.lorem_ipsum)
                LItxt.setTextColor(ContextCompat.getColor(applicationContext,R.color.st_tropaz))
            }
            in 17.0..18.5 ->{
                LItxt.text=getString(R.string.bmi_niedowaga)+getString(R.string.lorem_ipsum)
                LItxt.setTextColor(ContextCompat.getColor(applicationContext,R.color.verdigris))
            }
            in 18.5..25.5 ->{
                LItxt.text=getString(R.string.bmi_norma)+getString(R.string.lorem_ipsum)
                LItxt.setTextColor(ContextCompat.getColor(applicationContext,R.color.trinidad))
            }
            in 25.5..30.0 ->{
                LItxt.text=getString(R.string.bmi_nadwaga)+getString(R.string.lorem_ipsum)
                LItxt.setTextColor(ContextCompat.getColor(applicationContext,R.color.guardsman_red))
            }
            in 30.0..35.0 ->{
                LItxt.text=getString(R.string.bmi_IstOtyl)+getString(R.string.lorem_ipsum)
                LItxt.setTextColor(ContextCompat.getColor(applicationContext,R.color.guardsman_red))
            }
            in 35.0..40.0 ->{
                LItxt.text=getString(R.string.bmi_IIstOtyl)+getString(R.string.lorem_ipsum)
                LItxt.setTextColor(ContextCompat.getColor(applicationContext,R.color.amethyst))
            }
            else -> {
                LItxt.text=getString(R.string.bmi_IIIstOtyl)+getString(R.string.lorem_ipsum)
                LItxt.setTextColor(ContextCompat.getColor(applicationContext,R.color.amethyst))
            }
        }
    }
}
