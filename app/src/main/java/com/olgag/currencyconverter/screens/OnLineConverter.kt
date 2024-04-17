package com.olgag.currencyconverter.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.COUNTRIES
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.components.*
import com.olgag.currencyconverter.euroCurrency
import com.olgag.currencyconverter.model.CurrencyRow
import com.olgag.currencyconverter.model.Currency
import com.olgag.currencyconverter.model.CurrencyConverterViewModel
import com.olgag.currencyconverter.utils.ConvertDate
import java.time.LocalDate

@Composable
fun OnLineConverter(openDrawer: () -> Unit, viewModel: CurrencyConverterViewModel, currencies : List<Currency>) {
    var currencyFrom: Currency by remember { mutableStateOf(if(currencies.isEmpty()) Currency() else currencies[0])}
    var currencyTo: Currency by remember { mutableStateOf(if(currencies.isEmpty()) Currency() else currencies[1])}
    var currencyAmount: String by remember { mutableStateOf(if(currencies.isEmpty()) "" else "1") }
    val rate by viewModel.converter.observeAsState(0.0f)
    var currencyId: String by remember { mutableStateOf("") }
    val searchIdResults by viewModel.searchCurrencyById.observeAsState(CurrencyRow())
    val isSearching by viewModel.isSearching.observeAsState(false)
    var isOpenSearchBarFrom by remember { mutableStateOf(false) }
    var isOpenSearchBarTo by remember { mutableStateOf(false) }

    LaunchedEffect(currencyFrom, currencyTo) {
        currencyId = "${currencyFrom.currencyId}_${currencyTo.currencyId}"
        if (currencyId.length == 7) {
            viewModel.findCurrencyByID(currencyId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.background,
                        MaterialTheme.colors.secondary
                    )
                )
            )
    ) {
        TopBar(
            title = stringResource(id = R.string.currency_conversion_rates),
            buttonIcon = Icons.Rounded.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        )
        {
            Text(
                text = stringResource(id = R.string.select_currency),
                style = MaterialTheme.typography.caption
            )
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            ) {
                if (currencyFrom.currencyId.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.from),
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(top = 15.dp, end = 10.dp)
                    )
                    COUNTRIES?.let {
                        SearchBar(
                            list = it,
                            setCurrency = { currency -> currencyFrom = if(currency.currencyId.equals("EUR")) euroCurrency else currency },
                            active = isOpenSearchBarFrom,
                        )
                    }
                } else {
                    TxtButton(
                        massage = currencyFrom.currencyName,
                        painterResource = R.drawable.close,
                        onButtonClicked = {
                            currencyFrom = Currency()
                            isOpenSearchBarFrom = true
                        },
                        flagResource = currencyFrom.imgResourceId
                    )
               }
            }
            if (currencyId.length == 7) {
                Row() {
                    TxtButton("", R.drawable.currency_exchange,
                        onButtonClicked = {
                            val tmpCur =
                                Currency(currencyFrom.currencyName, currencyFrom.currencyId, currencyFrom.imgResourceId)
                            currencyFrom = Currency(currencyTo.currencyName, currencyTo.currencyId, currencyTo.imgResourceId)
                            currencyTo = Currency(tmpCur.currencyName, tmpCur.currencyId, tmpCur.imgResourceId)
                            viewModel.fetchConverter("${currencyFrom.currencyId}_${currencyTo.currencyId}")
                        }
                    )
                    if (!isSearching) {
                        if(searchIdResults == null){
                            AddToFavorite(rate, viewModel, currencyId, currencyFrom.currencyName, currencyTo.currencyName, currencyFrom.imgResourceId, currencyTo.imgResourceId)
                        }
                        else{
                            RemoveFromFavorite( viewModel, currencyId)
                        }
                    }
                    else
                        TxtButton( "", R.drawable.bookmark, onButtonClicked = {}, isEnabled = false)
                    }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
                ) {
                if (currencyTo.currencyId.isEmpty() && currencyFrom.currencyId.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.to),
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(start = 10.dp, top = 15.dp, end = 10.dp)
                    )
                    COUNTRIES?.let {
                        SearchBar(
                            list = it,
                            setCurrency = { currency -> currencyTo = if(currency.currencyId.equals("EUR")) euroCurrency else currency },
                            active = isOpenSearchBarTo
                        )
                    }
                }
                if (currencyTo.currencyId.isNotEmpty()) {
                    TxtButton(
                        massage = currencyTo.currencyName,
                        painterResource = R.drawable.close,
                        onButtonClicked = {
                            currencyTo = Currency()
                            isOpenSearchBarTo = true
                       },
                        flagResource = currencyTo.imgResourceId,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            if (currencyId.length == 7) {
                Spacer(Modifier.padding(10.dp))
                TextNumberCommaField(
                    numberWithComa = currencyAmount,
                    setNumberWithComa = { countOfCurrency -> currencyAmount = countOfCurrency },
                    labelName =stringResource(id =  R.string.conversion_amounts),
                    placeholder = stringResource(id =  R.string.enter_amount),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                )
            }
            Spacer(Modifier.padding(5.dp))
            if (currencyId.length == 7 && currencyAmount.isNotBlank()) {
                Rate(
                    viewModel = viewModel,
                    amount = currencyAmount,
                    currencyFrom = currencyFrom,
                    currencyTo = currencyTo,
                    rate = rate
                )
            }
        }
    }
}

@Composable
private  fun AddToFavorite(
    rate: Float,
    viewModel: CurrencyConverterViewModel,
    currencyId: String,
    currencyFromName: String,
    currencyToName: String,
    imgFromResourceId: Int,
    imgToResourceId: Int,
) {
    TxtButton( "", R.drawable.bookmark_add,
        onButtonClicked = { viewModel.insertCurrency(
            CurrencyRow(currencyId,
                ConvertDate.setDateForApi(LocalDate.now()),
                rate,
                currencyFromName,
                currencyToName,
                imgFromResourceId,
                imgToResourceId
            )
        )
            viewModel.findCurrencyByID(currencyId)
        },
        isEnabled = rate != 0.0f
    )
}

@Composable
private  fun RemoveFromFavorite(
    viewModel: CurrencyConverterViewModel,
    currencyId: String,
) {
    TxtButton( "", R.drawable.bookmark_remove,
        onButtonClicked = {  viewModel.deleteCurrency (currencyId)
                             viewModel.findCurrencyByID(currencyId)
                          },
    )
}