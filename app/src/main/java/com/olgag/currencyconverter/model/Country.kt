package com.olgag.currencyconverter.model

data class Results<T>(
    val results: Map<String, Country>
)

data class Country(
    val currencyId: String,
    val currencyName: String,
    var id: String,
    val name:  String,
    var resourceId: Int,
)



