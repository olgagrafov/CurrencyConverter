package com.olgag.currencyconverter.api_service

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.olgag.currencyconverter.IS_INTERNEAT_AVAILABLE
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.euroCurrency
import com.olgag.currencyconverter.model.*

class CurrencyConverterRepository {
    private lateinit var context: Context
    constructor()
    constructor(context: Context) {
        this.context = context
    }

    private val converterService = RetrofitHelper.converterService
    private val countryService = RetrofitHelper.countryService


    suspend fun getConverter(currency: String): Float {
         try {
            val usage = countryService.getUsage()
            if (usage.usage <= ApiConstants.API_REQUEST_PER_HOUR) {
                val convertedValue: CurrencyConverter =
                    converterService.getConverter(currency).values.first()
                return convertedValue.rate
            }
        } catch (e: Exception) {
             Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            return 0f
        }
        return 0f
    }

    suspend fun getConverterOfSpecificDay(currency: String, newDate: String): Float {
        try {
            val usage = countryService.getUsage()
            if (usage.usage <= ApiConstants.API_REQUEST_PER_HOUR) {
                val convertedValue: Map<String, Float> =
                    converterService.getConverterPerDay(currency, newDate).values.first() as Map<String, Float>
                return convertedValue.values.first().toFloat()
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            return 0f
        }
        return 0f
    }

    @SuppressLint("DiscouragedApi")
    suspend fun getCountries(): List<Country> {
        try {
            val usage = countryService.getUsage()
            if (usage.usage <= ApiConstants.API_REQUEST_PER_HOUR) {
                val response = countryService.getCurrencies()
                if (response.isSuccessful) {
                    val l: MutableList<Country>?= response.body()?.results?.values?.toMutableList()
                        l?.sortedBy { country -> country.name }?.find { it.id == "DO" }?.id = "doo"
                    val packageName = context.packageName
                    val length = l!!.size
                    for (i in 0 until length) {
                        val resourceId = context.resources.getIdentifier(l[i].id.lowercase(), "drawable", packageName)
                        if (resourceId > 0)
                            l[i].resourceId = resourceId
                        else
                            l[i].resourceId =  R.drawable.no_image
                    }
                    euroCurrency = Currency("European euro", "EUR", context.resources.getIdentifier("eu", "drawable", context.packageName))
                    l.add( Country(euroCurrency.currencyId, euroCurrency.currencyName, "eu", "United Europe", euroCurrency.imgResourceId))
                    l.add( Country("BTC", "Bitcoin", "btc", "Bitcoin", context.resources.getIdentifier("btc", "drawable", packageName)))
                   return  l.sortedBy { country -> country.name }
                } else {
                    Toast.makeText(context, "Failed to retrieve{$response.toString()}", Toast.LENGTH_SHORT).show()
                    IS_INTERNEAT_AVAILABLE = false
                    return emptyList()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            IS_INTERNEAT_AVAILABLE = false
            return emptyList()
        }
        IS_INTERNEAT_AVAILABLE = false
        return emptyList()
    }
}
