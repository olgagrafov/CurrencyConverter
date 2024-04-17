package com.olgag.currencyconverter.utils

import android.icu.text.NumberFormat
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.*

class NumberCommaTransformation : VisualTransformation {
    private fun Double?.formatWithComma(): String =
        NumberFormat.getNumberInstance(Locale.US).format(this ?: 0.0)

    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = text.text.toDoubleOrNull()?.formatWithComma() ?: ""
        return TransformedText(
            text = AnnotatedString(transformedText),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return transformedText.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return text.length
                }
            }
        )
    }
}

object PrintNumberHelper{

    fun printFloat(range: Float?): String {
        return range?.toBigDecimal().toString()
    }

    fun printInt(numberToTransform: String): String{
        return try {
            val numberComaTransformation = NumberCommaTransformation()
            numberComaTransformation.filter(AnnotatedString(numberToTransform)).text.text
        } catch (e: Exception){
            numberToTransform
        }
    }

    fun formatFloatValue(value: String): String {
        return if (value.isEmpty() || value == ".") {
            ""
        } else {
            val floatValue = value.toFloatOrNull()
            floatValue?.toString() ?: ""
        }
    }

    fun printMultiplicationAnswer(amount: String, coefficient: Float, conversionName: String): String {
        val answer = ""
        if (amount.isEmpty()) return answer
        return answer.plus(amount).plus(" ")
            .plus(conversionName.substringBefore(" to"))
            .plus( "\nit will be:\n")
            .plus(PrintNumberHelper.printFloat(amount.toLong() * coefficient))
            .plus(" ").plus(conversionName.substringAfter(" to "))
    }

    fun printTemperatureAnswer(amount: String, coefficient: Float, conversionName: String): String {
        val answer = ""
        if (amount.isEmpty()) return answer
        var temperature: Float = if (amount.substring(0,1).equals("-") && amount.length == 1) return answer
            else if (amount.substring(0,1).equals("-") && amount.length > 1)  amount.substring(1).toFloat().unaryMinus()
            else amount.toFloat()
        when (conversionName) {
            "Celsius to Fahrenheit" -> {
                //(0 °C × 9/5) + 32 = 32 °F
                temperature = (temperature * 9/5F) + coefficient
            }
            "Celsius to Kelvin" -> {
                //0 °C + 273,15 = 273,15 K
                temperature += coefficient
            }
            "Fahrenheit to Kelvin" -> {
                //(0 °F − 32) × 5/9 + 273,15 = 255,372 K
                temperature = (temperature - 32F) * 5/9 + coefficient
            }
            "Fahrenheit to Celsius" -> {
                 // (0 °F − 32) × 5/9 = -17,78 °C
                temperature = (temperature - coefficient) * 5/9
            }
            "Kelvin to Fahrenheit" -> {
                //(0 K − 273,15) × 9/5 + 32 = -335,5 °F
                temperature = (temperature - coefficient) * 9/5 + 32F
            }
            "Kelvin to Celsius" -> {
                //0 K − 273,15 = -273,1 °C
                temperature -= coefficient
            }
        }
        return answer.plus(amount).plus(" ")
            .plus(conversionName.substringBefore(" to"))
            .plus( "\nit will be:\n")
            .plus(temperature.toString())
            .plus(" ").plus(conversionName.substringAfter(" to "))
    }
}

//class NumberCommaTransformation : VisualTransformation {
//    private fun Int?.formatWithComma(): String =
//        NumberFormat.getNumberInstance(Locale.US).format(this ?: 0)
//
//    override fun filter(text: AnnotatedString): TransformedText {
//        return TransformedText(
//            text = AnnotatedString(text.text.toIntOrNull().formatWithComma()),
//            offsetMapping = object : OffsetMapping {
//                override fun originalToTransformed(offset: Int): Int {
//                    return text.text.toIntOrNull().formatWithComma().length
//                }
//
//                override fun transformedToOriginal(offset: Int): Int {
//                    return text.length
//                }
//            }
//        )
//    }
//}
/*
 //  onValueChange = {  if (it.length <= maxChar) currencyCount = it },
 //visualTransformation = TransformationWithComa(),
class TransformationWithComa : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val trimmed = if (text.text.length >= 12) text.text.substring(0..11) else text.text
        var formattedText = ""
        for (i in trimmed.indices) {
            formattedText += trimmed[i]
            if (i % 3 == 2 && i != 11) formattedText += ","
        }
        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset + 1
                    offset <= 8 -> offset + 2
                    offset <= 11 -> offset + 3
                    else -> 15
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 7 -> offset - 1
                    offset <= 11 -> offset - 2
                    offset <= 15 -> offset - 3
                    else -> 12
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }
}


        val s = range.toString()
        val ss = s.substring(0, s.indexOf('.')) // zelaia chast'
      //  val pp = s.substring(s.indexOf('.') + 1)//range?.toBigDecimal()//?.remainder(BigDecimal.ONE)
        val tt = range?.toBigDecimal()?.remainder(BigDecimal.ONE).toString()
        val ff = range?.toBigDecimal().toString()
        val pp = tt.substring(tt.indexOf('.') + 1)// disimal.naia chact'
        var i = 0
        while (i < pp.length) {
            if(pp.get(i) > '0')
                break;
            i++;
        }
        Log.i("hh", i.toString())
        //pp.filterIndexed { index, c ->  Log.i("hh", c + "-" + index.toString())  }
        Log.i("fff",ss + " - " +   pp + " - " + s + " - " + tt + "--" + ff)

       // val decimalFormat = DecimalFormat("#.##",  DecimalFormatSymbols(Locale.US))//DecimalFormat("#.##########",  DecimalFormatSymbols(Locale.US))\
        //decimalFormat.format(range)
 */