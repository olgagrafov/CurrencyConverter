package com.olgag.currencyconverter.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import com.olgag.currencyconverter.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.olgag.currencyconverter.DB_LAST_UPDATE
import com.olgag.currencyconverter.IS_DB_UPDATED
import com.olgag.currencyconverter.IS_INTERNEAT_AVAILABLE
import com.olgag.currencyconverter.model.CurrencyConverterViewModel
import com.olgag.currencyconverter.components.*
import com.olgag.currencyconverter.model.Currency
import com.olgag.currencyconverter.model.CurrencyRow
import com.olgag.currencyconverter.utils.ConvertDate
import kotlin.text.Typography.nbsp

@Composable
fun OffLineConverter(
    openDrawer: () -> Unit,
    viewModel: CurrencyConverterViewModel,
    navController: NavHostController
) {
    val navController = navController
    val allCurrencies by viewModel.allCurrencies.observeAsState(listOf())
    val searchResults by viewModel.searchCurrencyResults.observeAsState(listOf())

    var calculateCurrency: CurrencyRow by remember { mutableStateOf(CurrencyRow()) }

    var currencyName by remember { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }

    val onCurrencyName = { text: String ->
        currencyName = text
        searching = true
        viewModel.findCurrencyByName(currencyName)
    }
    val onClearClicked = {
        currencyName = ""
        searching = false
    }

    val list = if (searching) searchResults else allCurrencies
    var expanded by remember { mutableStateOf(false) }
    var calculate by remember { mutableStateOf(false) }

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
            title = stringResource(id = if (calculate) R.string.rate_calculator else R.string.saved_currency_conversion),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (calculate || allCurrencies.isEmpty()) {
                 OffLineRateConversion(onCloseClicked = { calculate = false }, isShowClose = allCurrencies.isNotEmpty())
            }
            else {
                Text(
                    text = if (IS_DB_UPDATED)  stringResource(id = R.string.updated_on).plus("$nbsp").plus(
                        DB_LAST_UPDATE?.let {
                            ConvertDate.printDateFromStringAsString(
                                it
                            )
                        }
                    ) else  stringResource(id = R.string.list_saved_rates),
                    style = MaterialTheme.typography.caption
                )
                AnimatedRateConversionByList(
                    expanded,
                    calculateCurrency,
                    onCloseClicked = { expanded = false })

                CustomTextField(
                    txtValue = currencyName,
                    onTextChange = onCurrencyName,
                    onTrailingIconClicked = onClearClicked,
                    modifier = if (expanded) (Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp, top = 20.dp)) else (Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp)),
                    labelTxt = stringResource(id = R.string.currency_name),
                    placeholder = stringResource(id = R.string.enter_currency_name),
                    keyboardType = KeyboardType.Text,
                    painterResourceForLeadingIcon = R.drawable.search,
                )
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        items(list) { currency ->
                            CurrencyItem(currency,
                                onDeleteClicked = {
                                    searching = false
                                    viewModel.deleteCurrency(currency.currencyStorageId)
                                },
                                onItemClicked = {
                                    if (IS_INTERNEAT_AVAILABLE) {
                                        val gson: Gson = GsonBuilder().create()
                                        val listCur: List<Currency> = listOf(
                                            Currency(
                                                currency.currencyFromName,
                                                currency.currencyStorageId.substring(0, 3),
                                                currency.imgFromResourceId
                                            ),
                                            Currency(
                                                currency.currencyToName,
                                                currency.currencyStorageId.substring(4, 7),
                                                currency.imgToResourceId
                                            )
                                        )
                                        val currencyJson = gson.toJson(listCur)
                                        navController.navigate(
                                            "${DrawerScreens.OnLineConverter.route}?currencies={currencies}"
                                                .replace(
                                                    oldValue = "{currencies}",
                                                    newValue = currencyJson
                                                )
                                        ) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    } else {
                                        currencyName = ""
                                        searching = false
                                        expanded = true
                                        calculateCurrency = currency
                                    }
                                }
                            )
                        }
                    }
                    TextButton(
                        onClick = { calculate = true },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.calculator),
                            contentDescription = "",
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                        )
                    }
                }
            }
        }
    }
}



