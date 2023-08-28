package com.olgag.currencyconverter.api_service

import com.olgag.currencyconverter.api_service.ApiConstants.Companion.API_CONVERTER
import com.olgag.currencyconverter.api_service.ApiConstants.Companion.API_COUNTRIES
import com.olgag.currencyconverter.api_service.ApiConstants.Companion.API_USAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.olgag.currencyconverter.API_KEY
import com.olgag.currencyconverter.model.*

interface CurrencyConverterService {
    @GET(API_USAGE)
    suspend fun getUsage(@Query("apiKey") _apiKey: String? = API_KEY): Usage

    @GET(API_COUNTRIES)
    suspend fun getCurrencies(@Query("apiKey") _apiKey: String? = API_KEY): Response<Results<Map<String, Country>>>

    @GET(API_CONVERTER)
    suspend fun getConverter(@Query("q") currency: String,
                             @Query("apiKey") _apiKey: String? = API_KEY,
                             @Query("compact") compact: String  = "y"): Map<String, CurrencyConverter>

    @GET(API_CONVERTER)
    suspend fun getConverterPerDay(@Query("q") currency: String,
                                   @Query("date") newDate: String,
                             @Query("apiKey") _apiKey: String? = API_KEY,
                             @Query("compact") compact: String  = "ultra"): Map<String, Any>

}