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
import androidx.core.text.isDigitsOnly
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.utils.NumberCommaTransformation

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextNumberCommaField(
    numberWithComa: String,
    setNumberWithComa: (String) -> Unit,
    labelName: Int,
    placeholder: Int,
    modifier: Modifier = Modifier){
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value =  numberWithComa,
        onValueChange = { newText: String ->
            if (newText.length <= Int.MAX_VALUE.toString().length && newText.isDigitsOnly()) {
                setNumberWithComa(newText)
            }
        },
        label = { Text(text = stringResource(id = labelName), style = MaterialTheme.typography.h6) },
        placeholder = { Text(text = stringResource(id = placeholder), style = MaterialTheme.typography.h6) },
        textStyle = MaterialTheme.typography.h2,
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            IconButton(onClick = { setNumberWithComa("") }) {
                Icon( painter = painterResource(R.drawable.close) , contentDescription = "")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        visualTransformation = NumberCommaTransformation(),
        modifier = modifier
    )
}
