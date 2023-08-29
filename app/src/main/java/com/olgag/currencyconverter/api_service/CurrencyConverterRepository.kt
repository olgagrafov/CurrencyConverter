package com.olgag.currencyconverter.api_service

import android.annotation.SuppressLint
import android.content.Context
import com.olgag.currencyconverter.IS_INTERNEAT_AVAILABLE
import com.olgag.currencyconverter.R
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
            //Log.e("errRepository", "Failed to retrieve{$e.toString()}")
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
               // Log.e("Repository", convertedValue.values.first().toString())
                return convertedValue.values.first().toFloat()
            }
        } catch (e: Exception) {
          //  Log.e("errRepository", "Failed to retrieve{$e.toString()}")
            return 0f
        }
        return 0f
    }

    @SuppressLint("DiscouragedApi")
    suspend fun getCountries(): List<Country> {
        try {
            val usage = countryService.getUsage()
            //Log.e("usage", usage.usage.toString())
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

                    l.add( Country("BTC", "Bitcoin", "btc", "Bitcoin", context.resources.getIdentifier("btc", "drawable", packageName)) )
                  // Log.e("list",  l.size.toString() +  l[l.size-1].toString())//Country(currencyId=AFN, currencyName=Afghan afghani, id=AF, name=Afghanistan, resourceId=2131165307)
                    return  l.sortedBy { country -> country.name }
                } else {
                  //  Log.e("errRepository", "Failed to retrieve{$response.toString()}")
                    IS_INTERNEAT_AVAILABLE = false
                    return emptyList()
                }
            }
        } catch (e: Exception) {
            //Log.e("errRepository", "Failed to retrieve{$e.toString()}")
            IS_INTERNEAT_AVAILABLE = false
            return emptyList()
        }
        IS_INTERNEAT_AVAILABLE = false
        return emptyList()
    }
}