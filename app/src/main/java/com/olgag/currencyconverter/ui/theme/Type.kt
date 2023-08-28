package com.olgag.currencyconverter.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.olgag.currencyconverter.R

val WorkSansFont = FontFamily (
    Font(R.font.work_sans_regular)
)

val Typography = Typography(
    caption = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W900,
        fontSize = 20.sp,
        color = Yellow700,
        textAlign = TextAlign.Center
    ),
    h1 = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W900,
        fontSize = 16.sp,
        color = White,
    ),
    h2 = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W900,
        fontSize = 18.sp,
        color = Yellow700,
    ),
    h3 = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        color = Yellow700
    ),
    h4 = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W300,
        fontSize = 12.sp,
        color = WhiteDark,
        fontStyle = FontStyle.Italic
    ),
    h5 = TextStyle(//text for isEnabled button
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W900,
        fontSize = 18.sp,
        color = WhiteDark,
    ),
    h6 = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W300,
        fontSize = 14.sp,
        color = Yellow700,
        fontStyle = FontStyle.Italic
    ),
    body1 = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W900,
        fontSize = 18.sp,
        color = Yellow700,
    ),
    body2 = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        color = Yellow700,
    ),
    overline = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
        color = Yellow800,
        textAlign = TextAlign.Center
    ),
    button = TextStyle(
        fontFamily = WorkSansFont,
        fontWeight = FontWeight.W900,
        fontSize = 14.sp,
        color = Yellow700,
        textAlign = TextAlign.Center,
    ),
)