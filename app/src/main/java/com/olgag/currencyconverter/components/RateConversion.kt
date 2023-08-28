package com.olgag.currencyconverter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.utils.PrintNumberHelper

@Composable
fun RateConversion(rate: Float?) {
    var currencyAmount: String by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf( rate) }
    val amountPerRate =  if (currencyAmount.isNotEmpty()) {
        rate?.times(currencyAmount.toLong())
    } else 0F

    FloatOnlyTextField(
        value = rate.toString(),
        onValueChange = { newRate: Float -> rate = newRate },
        labelName = R.string.currency_rate,
        modifier = Modifier.width(300.dp)
    )

    TextNumberCommaField(
        numberWithComa = currencyAmount,
        setNumberWithComa = { countOfCurrency -> currencyAmount = countOfCurrency },
        labelName = R.string.conversion_amounts,
        placeholder = R.string.enter_amount,
        modifier = Modifier.width(300.dp).padding(top = 30.dp, bottom = 30.dp)
    )

    if(currencyAmount.isNotEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.total),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = PrintNumberHelper.printFloat(amountPerRate),
                style = MaterialTheme.typography.overline,
            )
        }
    }
}