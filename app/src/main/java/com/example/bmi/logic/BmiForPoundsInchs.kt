package com.example.bmi.logic

class BmiForPoundsInchs(var mass : Int, var height : Int): Bmi   {

    override fun countBmi() : Double {
        return mass*703.00/(height*height)
    }
}