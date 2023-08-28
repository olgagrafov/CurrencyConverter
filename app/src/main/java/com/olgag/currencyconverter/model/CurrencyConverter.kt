package com.olgag.currencyconverter.model

import com.google.gson.annotations.SerializedName

data class CurrencyConverter(@SerializedName("val") val rate: Float)
