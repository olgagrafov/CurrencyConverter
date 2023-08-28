package com.olgag.currencyconverter.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.olgag.currencyconverter.model.CurrencyRow
import kotlinx.coroutines.*

class CurrencyRepository(private val currencyDao: CurrencyDao) {
    val allCurrencies: LiveData<List<CurrencyRow>> = currencyDao.getAllCurrencies()
    val searchResults = MutableLiveData<List<CurrencyRow>>()
    val currencyById = MutableLiveData<CurrencyRow>()
    val isSearching = MutableLiveData<Boolean>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun insertCurrency(newCurrency: CurrencyRow) {
        coroutineScope.launch(Dispatchers.IO) {
            currencyDao.insertCurrency(newCurrency)
        }
    }

    fun deleteCurrency(id: String) {
        coroutineScope.launch(Dispatchers.IO) {
            currencyDao.deleteCurrency(id)
        }
    }

    fun findCurrencyByName(currencyName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindByName(currencyName).await()
        }
    }

    private fun asyncFindByName(id: String): Deferred<List<CurrencyRow>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async currencyDao.findCurrencyByName(id)
        }


    fun findCurrencyById(currencyId: String) {
        coroutineScope.launch(Dispatchers.Main) {
            currencyById.value = asyncFindById(currencyId).await()
        }
    }

    private fun asyncFindById(id: String): Deferred<CurrencyRow> =
        coroutineScope.async(Dispatchers.IO) {
            isSearching.postValue(true) // Indicate that the search is ongoing
            val result = currencyDao.findCurrencyById(id)
            isSearching.postValue(false) // Indicate that the search is done
            return@async result
        }

}
