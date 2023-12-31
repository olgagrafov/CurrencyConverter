package com.olgag.currencyconverter.api_service

class ApiConstants {
    companion object {
        const val API_REQUEST_PER_HOUR = 100
        const val URL_COUNTRIES   = "https://free.currconv.com/"
        const val API_USAGE       = "others/usage"
        const val API_COUNTRIES   = "api/v7/countries"
        const val URL_CONVERTERS  = "https://free.currencyconverterapi.com/"
        const val API_CONVERTER   = "api/v5/convert"
    }
}

//usage : "https://free.currconv.com/others/usage?apiKey="
//https://free.currconv.com/api/v7/currencies?apiKey=
//https://free.currconv.com/api/v7/countries?apiKey=
//https://free.currencyconverterapi.com/api/v5/convert?apiKey&compact=y&q=USD_BTC
//https://free.currencyconverterapi.com/api/v5/convert?q=USD_BTC,BTC_USD&compact=ultra&date=2023-08-22&apiKey=