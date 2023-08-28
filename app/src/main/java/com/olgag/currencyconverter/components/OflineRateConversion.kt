package com.olgag.currencyconverter.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.R

@Composable
fun OffLineRateConversion(onCloseClicked: () -> Unit, isShowClose: Boolean) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isShowClose) {
            IconButton(
                onClick = { onCloseClicked() },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = null
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            if (isShowClose) {
                Spacer(modifier = Modifier.padding(5.dp))
            }
            Text(
                text = stringResource(id = R.string.convert_any_amount_to_any_currency),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            RateConversion(rate = 0F)
        }
    }
}
