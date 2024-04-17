package com.olgag.currencyconverter.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TabViewModelFactory (val application: Application) :
ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyConverterViewModel(application) as T
    }
}