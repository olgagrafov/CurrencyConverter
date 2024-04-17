package com.olgag.currencyconverter.screens

import android.content.Context
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.components.DropdownMenu
import com.olgag.currencyconverter.components.TextNegativeNumber
import com.olgag.currencyconverter.components.TextNumberCommaField
import com.olgag.currencyconverter.model.TabViewModel
import com.olgag.currencyconverter.utils.*

@Composable
fun ConversionScreen(viewModel: TabViewModel) {

    val context: Context =  LocalContext.current.applicationContext
    val currentIdTab by viewModel.tabIndex.observeAsState(initial = 0)
    val conversionList = remember { mutableMapOf<String, Float>() }
    var conversionName by remember { mutableStateOf("") }
    var isSearchBarActive by remember { mutableStateOf(false) }
    var coefficient by remember { mutableStateOf(0f) }
    var conversionAmount by remember { mutableStateOf( "") }
    val title = stringResource(tabs[currentIdTab].titleStringRes)
    var isTemperatureTab by remember { mutableStateOf( false) }
    LaunchedEffect(currentIdTab) {
        conversionList.clear()
        isSearchBarActive = false
        isTemperatureTab = false
        conversionName = ""
        coefficient = 0f
        conversionAmount = ""
        when (currentIdTab) {
            ConversionScreens.ConversionLength.idTab ->
                lengthList.forEach { conversionList[context.getString(it.lengthFrom).plus(" to ").plus(context.getString(it.lengthTo))] = it.coefficient }
            ConversionScreens.ConversionWeight.idTab ->
                weightList.forEach {  conversionList[context.getString(it.weightFrom).plus(" to ").plus(context.getString(it.weightTo))] = it.coefficient }
            ConversionScreens.ConversionTemperature.idTab -> {
                temperatureList.forEach {
                    conversionList[context.getString(it.temperatureFrom).plus(" to ")
                        .plus(context.getString(it.temperatureTo))] = it.coefficient
                }
                isTemperatureTab = true
            }

        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
        .verticalScroll(rememberScrollState())
        .draggable(
            state = viewModel.dragState.value!!,
            orientation = Orientation.Horizontal,
            onDragStarted = { },
            onDragStopped = {
                viewModel.updateTabIndexBasedOnSwipe()
            }),
        horizontalAlignment = Alignment.CenterHorizontally) {
       if(conversionList.isNotEmpty()){
           Text(
               text = stringResource(R.string.select_conversion, title),
               style = MaterialTheme.typography.caption
           )
           Row(
               horizontalArrangement = Arrangement.Start,
               verticalAlignment = Alignment.Top,
               modifier = Modifier.padding(top = 30.dp)
           ) {
               DropdownMenu(
                   conversionList,
                   title,
                   active = isSearchBarActive,
                   setActive = { act -> isSearchBarActive = act },
                   text = conversionName,
                   setNameOfConversion = { txt -> conversionName = txt },
                   setCoefficientOfConversion = { coef -> coefficient = coef }
               )
           }
           if(isTemperatureTab) {
               TextNegativeNumber(
                   numberWithComa = conversionAmount,
                   setNumberWithComa = { amount -> conversionAmount = amount },
                   labelName = title.plus(": ").plus(conversionName),
                   placeholder = stringResource(
                       R.string.enter_conversion_amount,
                       conversionName.substringBefore(" to")
                   ),
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 30.dp)
               )
               Text(
                   text = PrintNumberHelper.printTemperatureAnswer(conversionAmount, coefficient, conversionName),
                   style = MaterialTheme.typography.overline
               )
           } else {
               TextNumberCommaField(
                   numberWithComa = conversionAmount,
                   setNumberWithComa = { amount -> conversionAmount = amount },
                   labelName = title.plus(": ").plus(conversionName),
                   placeholder = stringResource(
                       R.string.enter_conversion_amount,
                       conversionName.substringBefore(" to")
                   ),
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 30.dp)
               )
               Text(
                   text = PrintNumberHelper.printMultiplicationAnswer(conversionAmount, coefficient, conversionName),
                   style = MaterialTheme.typography.overline
               )
           }
        }
        else{
           Text(
               text = "Something wrong",
               style = MaterialTheme.typography.overline,
           )
       }
    }
}
