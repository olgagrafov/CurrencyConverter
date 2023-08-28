package com.olgag.currencyconverter.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.utils.PrintNumberHelper


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FloatOnlyTextField(
    value: String,
    onValueChange: (Float) -> Unit,
    labelName: Int,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var formattedValue by remember(value)  {  mutableStateOf(PrintNumberHelper.formatFloatValue(value)) }
    OutlinedTextField(
        value = formattedValue,
        onValueChange = {
            val cleanValue = it.filter { char -> char.isDigit() || char == '.' }
            formattedValue = PrintNumberHelper.formatFloatValue(cleanValue)
            if (cleanValue.isNotBlank()) {
                onValueChange(cleanValue.toFloatOrNull() ?: 0f)
            } else {
                onValueChange(0f)
            }
        },
        label = { Text(text = stringResource(id = labelName), style = MaterialTheme.typography.h6) },
        textStyle = MaterialTheme.typography.h2,
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            IconButton(onClick = {
                formattedValue = "0.0"
                onValueChange(0f)
            }) {
                Icon( painter = painterResource(R.drawable.close) , contentDescription = "")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        modifier = modifier,
    )
}
