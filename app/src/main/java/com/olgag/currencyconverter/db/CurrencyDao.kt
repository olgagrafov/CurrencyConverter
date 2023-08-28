package com.olgag.currencyconverter.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.olgag.currencyconverter.model.CurrencyRow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrency(currency: CurrencyRow)

    @Query("SELECT * FROM currencies WHERE currencyId = :id")
    fun findCurrencyById(id: String): CurrencyRow

    @Query("SELECT * FROM currencies WHERE LOWER(currencyFromName) LIKE '%' || LOWER(:currencyName) || '%' OR LOWER(currencyToName) LIKE '%' || LOWER(:currencyName) || '%'")
    fun findCurrencyByName(currencyName: String): List<CurrencyRow>


    @Query("DELETE FROM currencies WHERE currencyId = :id")
    fun deleteCurrency(id: String)

    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): LiveData<List<CurrencyRow>>
}