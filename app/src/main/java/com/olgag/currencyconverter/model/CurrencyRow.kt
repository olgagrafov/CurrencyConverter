package com.olgag.currencyconverter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
class CurrencyRow {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "currencyId")
    var currencyStorageId: String = ""
    var currencyRangeDate: String = ""
    var rate: Float? = 0.0f
    var currencyFromName: String = ""
    var currencyToName: String = ""
    var imgFromResourceId: Int = 0
    var imgToResourceId: Int = 0

    constructor() {}

    constructor(currencyStorageId: String,
                currencyRangeDate: String,
                rate: Float = 0F,
                currencyFromName: String,
                currencyToName: String,
                imgFromResourceId: Int,
                imgToResourceId: Int) {
        this.currencyStorageId = currencyStorageId
        this.currencyRangeDate = currencyRangeDate
        this.rate = rate
        this.currencyFromName  = currencyFromName
        this.currencyToName = currencyToName
        this.imgFromResourceId = imgFromResourceId
        this.imgToResourceId = imgToResourceId
    }
}
