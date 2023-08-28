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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(
    txtValue: String,
    onTextChange: (String) -> Unit,
    onTrailingIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
    labelTxt: String,
    keyboardType: KeyboardType,
    placeholder: String = "",
    painterResourceForLeadingIcon: Int = 0,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = txtValue,
        onValueChange = {
            onTextChange(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        leadingIcon = {
            if (painterResourceForLeadingIcon>0) {
                Icon(painter = painterResource(painterResourceForLeadingIcon), contentDescription = "")
            }
        },
        trailingIcon = {
            IconButton(onClick = { onTrailingIconClicked() }) {
                Icon(painter = painterResource(R.drawable.close), contentDescription = "")
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.h2,
        shape = RoundedCornerShape(16.dp),
        label = { Text(text = labelTxt, style = MaterialTheme.typography.h6) },
        placeholder = { Text(text = placeholder, style = MaterialTheme.typography.h6) },
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        modifier = modifier,
    )
}
