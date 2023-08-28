package com.olgag.currencyconverter.utils

import java.time.LocalDate

object ConvertDate {
    fun printDateFromDateAsString(selectedDate: LocalDate): String{
        val strDay = if(selectedDate.dayOfMonth < 10) "0${selectedDate.dayOfMonth}" else "${selectedDate.dayOfMonth}"
        val strMonth = if(selectedDate.monthValue < 10) "0${selectedDate.monthValue}" else "${selectedDate.monthValue}"
        return "$strDay/$strMonth/${selectedDate.year}"
    }
    fun setDateForApi(selectedDate: LocalDate): String{
        val strDay = if(selectedDate.dayOfMonth < 10) "0${selectedDate.dayOfMonth}" else "${selectedDate.dayOfMonth}"
        val strMonth = if(selectedDate.monthValue < 10) "0${selectedDate.monthValue}" else "${selectedDate.monthValue}"
        return "${selectedDate.year}-$strMonth-$strDay"
    }

    fun printDateFromStringAsString(strDate: String): String{
        return strDate.substring(8,10).plus("/").plus(strDate.substring(5,7)).plus("/").plus(strDate.substring(0,4))
     }
}