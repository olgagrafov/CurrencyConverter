package com.olgag.currencyconverter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.model.Currency
import com.olgag.currencyconverter.model.CurrencyConverterViewModel
import com.olgag.currencyconverter.utils.ConvertDate
import com.olgag.currencyconverter.utils.PrintNumberHelper
import java.time.LocalDate

@Composable
fun Rate(
    viewModel: CurrencyConverterViewModel,
    amount: String,
    currencyFrom: Currency,
    currencyTo: Currency,
    rate: Float
) {
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchConverter("${currencyFrom.currencyId}_${currencyTo.currencyId}")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "For ${ConvertDate.printDateFromDateAsString(selectedDate.value)} , here is the rate:",
            style = MaterialTheme.typography.body2
        )
        Text(text = PrintNumberHelper.printFloat(rate),
            style = MaterialTheme.typography.overline,
        )
//        Row {
//            Text(
//                text = "from ",
//                style = MaterialTheme.typography.body2
//            )
//            Text(
//                text = currencyFrom.currencyName,
//                style = MaterialTheme.typography.body1
//            )
//        }
//        Row {
//            Text(
//                text = "to ",
//                style = MaterialTheme.typography.body2
//            )
//            Text(
//                text = currencyTo.currencyName,
//                style = MaterialTheme.typography.body1
//            )
//        }
        Text(
            text = stringResource(id = R.string.total),
            style = MaterialTheme.typography.body2
        )
        Row {
            Text(
                text = "for ",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = PrintNumberHelper.printInt(amount),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = " ${currencyFrom.currencyId} is",
                style = MaterialTheme.typography.body2
            )
        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = PrintNumberHelper.printFloat(rate * amount.toLong()),
                style = MaterialTheme.typography.overline,
            )
            Text(
                text =  " ${currencyTo.currencyId}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }
        TextButton(
            onClick = {openDialog.value = true },
            shape = MaterialTheme.shapes.small,
        ) {
            Image(
                painter = painterResource(R.drawable.calendar),
                contentDescription = "",
                modifier = Modifier
                    .width(96.dp)
                    .height(96.dp)
            )
        }
        if (openDialog.value) {
            CalendarDatePickerDialog(openDialog , selectedDate, onDateSelected = { selectedDate ->
                viewModel.fetchConverterOfSpecificDay("${currencyFrom.currencyId}_${currencyTo.currencyId}", ConvertDate.setDateForApi(selectedDate))
            })
        }
    }
}

