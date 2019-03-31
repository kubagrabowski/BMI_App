package com.example.bmi.logic

class BmiForKgCm(var mass : Int, var height : Int): Bmi   {

    override fun countBmi() : Double {
        return mass*10000.00/(height*height)
    }
}