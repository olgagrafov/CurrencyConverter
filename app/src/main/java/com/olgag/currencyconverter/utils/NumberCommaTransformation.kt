package com.olgag.currencyconverter.utils

import android.icu.text.NumberFormat
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
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
        val decimalFormat = DecimalFormat("#.##########",  DecimalFormatSymbols(Locale.US))
        return decimalFormat.format(range)
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
 */