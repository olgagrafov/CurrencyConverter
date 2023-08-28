package com.olgag.currencyconverter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TxtButton(
    massage: String,
    painterResource: Int,
    onButtonClicked: () -> Unit,
    rightIcon: Boolean = false,
    isEnabled: Boolean =  true,
    modifier: Modifier = Modifier,
    flagResource: Int = 0
    ){
    TextButton(
        onClick = { onButtonClicked() },
        shape = MaterialTheme.shapes.small,
        enabled = isEnabled,
        modifier = modifier
    ) {
        if(flagResource > 0) {
            Image(
                painter = painterResource(flagResource),
                contentDescription = null,
                modifier = Modifier.width(36.dp)
            )
        }
        if(rightIcon) {
            Image(
                painter = painterResource(painterResource),
                contentDescription = "",
                modifier = Modifier.width(48.dp).height(48.dp)
            )
        }
        if(massage.isNotEmpty()) {
            Text(
                text = massage,
                style = if (isEnabled) MaterialTheme.typography.h2 else MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        if(!rightIcon) {
            Icon(
                painter = painterResource(painterResource),
                contentDescription = ""
            )
        }
    }
}