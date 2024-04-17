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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.R

@Composable
fun TextNegativeNumber(
    numberWithComa: String,
    setNumberWithComa: (String) -> Unit,
    labelName: String,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = numberWithComa,
        onValueChange = { newText: String ->
            if (newText.isEmpty() || newText.isNumeric()) {
                setNumberWithComa(newText)
            }
        },
        label = { Text(text = labelName, style = MaterialTheme.typography.h6) },
        placeholder = { Text(text = placeholder, style = MaterialTheme.typography.h6) },
        textStyle = MaterialTheme.typography.h2,
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            IconButton(onClick = { setNumberWithComa("") }) {
                Icon(painter = painterResource(R.drawable.close), contentDescription = "")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        modifier = modifier
    )
}

fun String.isNumeric(): Boolean {
    return this.matches(Regex("-?\\d{0,3}"))//"-?\\d{0,3}(,\\d{0,3})*"
}