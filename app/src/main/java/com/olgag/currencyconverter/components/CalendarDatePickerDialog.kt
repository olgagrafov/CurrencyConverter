package com.olgag.currencyconverter.components

import com.olgag.currencyconverter.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun CalendarDatePickerDialog(
    openDialog: MutableState<Boolean>,
    newDate: MutableState<LocalDate>,
    onDateSelected: (date:LocalDate) -> Unit
) {
    val currentDate = LocalDate.now()
    val oneYearAgo = currentDate.minusYears(1)
    val allowedDateValidator: (LocalDate) -> Boolean = { selectedDate ->
        selectedDate.isAfter(oneYearAgo) && selectedDate.isBefore(currentDate.plusDays(1))
    }
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(R.string.ok))
            negativeButton(stringResource(R.string.cancel), onClick = { openDialog.value = false })
        },
        onCloseRequest = { openDialog.value = false }
    ) {
        datepicker(
            initialDate = newDate.value,
            title = stringResource(R.string.select_date),
            yearRange = IntRange(LocalDate.now().minusYears(1).year, LocalDate.now().year),
            allowedDateValidator =  allowedDateValidator ,
        ) { date ->
            newDate.value = date
            openDialog.value = false
            onDateSelected(date)
        }
    }
    dialogState.show()
}