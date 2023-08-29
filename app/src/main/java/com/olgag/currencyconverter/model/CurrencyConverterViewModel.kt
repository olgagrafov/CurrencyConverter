package com.olgag.currencyconverter.model


import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olgag.currencyconverter.API_KEY
import com.olgag.currencyconverter.DB_LAST_UPDATE
import com.olgag.currencyconverter.IS_DB_UPDATED
import com.olgag.currencyconverter.IS_INTERNEAT_AVAILABLE
import com.olgag.currencyconverter.api_service.CurrencyConverterRepository
import com.olgag.currencyconverter.db.CurrencyRepository
import com.olgag.currencyconverter.db.CurrencyRoomDatabase
import com.olgag.currencyconverter.utils.ConvertDate
import kotlinx.coroutines.launch
import java.time.LocalDate

class CurrencyConverterViewModel(application: Application): ViewModel() {
    private val app = application
    private var repository = CurrencyConverterRepository()
    private val _converter = MutableLiveData<Float>()
    val converter: LiveData<Float> = _converter

    private val currencyDb = CurrencyRoomDatabase.getInstance(application)
    private val currencyDao = currencyDb.currencyDao()
    private val dbRepository: CurrencyRepository = CurrencyRepository(currencyDao)

    val allCurrencies: LiveData<List<CurrencyRow>> = dbRepository.allCurrencies
    val searchCurrencyResults: MutableLiveData<List<CurrencyRow>> = dbRepository.searchResults
    val searchCurrencyById: LiveData<CurrencyRow> = dbRepository.currencyById
    val isSearching: LiveData<Boolean> = dbRepository.isSearching


    private val allCurrenciesObserver = Observer<List<CurrencyRow>> { currencies ->
        val totalCurrencies = currencies.size
        var processedCurrencies = 0
        currencies.forEach { currency ->
            viewModelScope.launch {
                try {
                    val newConverter = repository.getConverter(currency.currencyStorageId)
                    updateCurrencyRateOfSavedDate(currency, newConverter)
                } catch (e: Exception) {
                  // Log.i("error", e.toString())
                    IS_DB_UPDATED = false
                } finally {
                    processedCurrencies++

                    if (processedCurrencies == totalCurrencies) {
                        saveDBDateUpdate()
                    }
                    else {
                        IS_DB_UPDATED = false
                    }
                }
            }
        }
    }

    init {
        val sharedPreferences = application.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        DB_LAST_UPDATE = sharedPreferences.getString("lastUpdateTime", "")
        val currentTimeMillis = ConvertDate.setDateForApi(LocalDate.now())
        if(currentTimeMillis > DB_LAST_UPDATE!! && IS_INTERNEAT_AVAILABLE) {
            allCurrencies.observeForever(allCurrenciesObserver)
            DB_LAST_UPDATE = currentTimeMillis
        }
    }

    override fun onCleared() {
        allCurrencies.removeObserver(allCurrenciesObserver)
        super.onCleared()
    }

    private fun updateCurrencyRateOfSavedDate(currency: CurrencyRow, newConverter: Float) {
        currency.rate = newConverter
        currency.currencyRangeDate = DB_LAST_UPDATE.toString()
        dbRepository.insertCurrency(currency)
        allCurrencies.removeObserver(allCurrenciesObserver)
    }

    fun fetchConverter(currency: String) {
        if (!API_KEY.isNullOrBlank()) {
            viewModelScope.launch {
                try {
                    _converter.value = repository.getConverter(currency)
                } catch (e: Exception) {
                    //Log.e("err:", "Failed to retrieve getConverter $e")
                    _converter.value = 0F
                }
            }
        }
    }

    fun fetchConverterOfSpecificDay(currency: String, newDate: String) {
        if (!API_KEY.isNullOrBlank()) {
            viewModelScope.launch {
                try {
                    _converter.value = repository.getConverterOfSpecificDay(currency, newDate)
                } catch (e: Exception) {
                    //Log.e("err:", "Failed to retrieve getConverter $e")
                    _converter.value = 0F
                }
            }
        }
    }

    fun insertCurrency(currency: CurrencyRow) {
        dbRepository.insertCurrency(currency)
    }

    fun deleteCurrency(id: String) {
        dbRepository.deleteCurrency(id)
    }

    fun findCurrencyByName(currencyName: String) {
        dbRepository.findCurrencyByName(currencyName)
    }

    fun findCurrencyByID(id: String) {
        dbRepository.findCurrencyById(id)
    }

    private fun saveDBDateUpdate() {
        val sharedPreferences = app.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("lastUpdateTime", DB_LAST_UPDATE)
        editor.apply()
        IS_DB_UPDATED = true
    }
}
