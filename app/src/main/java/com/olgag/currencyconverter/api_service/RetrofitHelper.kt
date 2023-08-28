package com.olgag.currencyconverter.api_service

import com.olgag.currencyconverter.api_service.ApiConstants.Companion.URL_CONVERTERS
import com.olgag.currencyconverter.api_service.ApiConstants.Companion.URL_COUNTRIES
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

        private val country_retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL_COUNTRIES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val converter_retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL_CONVERTERS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val converterService: CurrencyConverterService by lazy {
        converter_retrofit.create(CurrencyConverterService::class.java)
    }

    val countryService: CurrencyConverterService by lazy {
        country_retrofit.create(CurrencyConverterService::class.java)
    }
}
