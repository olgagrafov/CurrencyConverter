package com.olgag.currencyconverter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.IS_DB_UPDATED
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.model.CurrencyRow
import com.olgag.currencyconverter.utils.ConvertDate
import com.olgag.currencyconverter.utils.PrintNumberHelper
import kotlin.text.Typography.nbsp

@Composable
fun CurrencyItem(
    currency: CurrencyRow,
    onDeleteClicked: () -> Unit,
    onItemClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small
            )
            .drawBehind {
                drawRoundRect(
                    Color(0xFFF7EFD5),
                    cornerRadius = CornerRadius(25.dp.toPx())
                )
            }
            .clickable { onItemClicked() }
        ) {
        Row(modifier = Modifier.padding(bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(15.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(currency.imgFromResourceId),
                        contentDescription = null,
                        modifier = Modifier.width(36.dp)
                    )
                    Text(
                        text = currency.currencyFromName,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(currency.imgToResourceId),
                        contentDescription = null,
                        modifier = Modifier.width(36.dp)
                    )
                    Text(
                        text = currency.currencyToName,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(10.dp)
                    )
                }

            }
            Column() {
                TxtButton("", R.drawable.delete, onButtonClicked = { onDeleteClicked() })
            }
        }
        Text(
            text = if(IS_DB_UPDATED) "The rate is".plus("$nbsp")
                     .plus(PrintNumberHelper.printFloat(currency.rate))
                   else "Rate updated on".plus("$nbsp")
                    .plus(ConvertDate.printDateFromStringAsString(currency.currencyRangeDate))
                    .plus("$nbsp").plus("is").plus("$nbsp")
                    .plus(PrintNumberHelper.printFloat(currency.rate)),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.align(alignment = Alignment.BottomCenter).padding(bottom = 10.dp)
        )
    }
}

//
//    var isVisible by remember { mutableStateOf(true) }
//
//
//    LaunchedEffect(isVisible){
//        if(!isVisible){
//            onDeleteClicked()
//        }
//    }
//
//    AnimatedVisibility(
//        visible = isVisible,
//        enter = slideInHorizontally(initialOffsetX = { it }),
//        exit = slideOutHorizontally(targetOffsetX = { it }),
//        modifier = Modifier.fillMaxWidth()
//   ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(5.dp)
//                .border(
//                    width = 1.dp,
//                    color = MaterialTheme.colors.primary,
//                    shape = MaterialTheme.shapes.small
//                )
//                .drawBehind {
//                    drawRoundRect(
//                        Color(0xFFF7EFD5),
//                        cornerRadius = CornerRadius(25.dp.toPx())
//                    )
//                }
//                .clickable { onItemClicked() }
//
//        ) {
//            Column(modifier = Modifier
//                .weight(1f)
//                .padding(15.dp)) {
//                Text(
//                    text = "${currency.currencyFromName} - \n${currency.currencyToName}",
//                    style = MaterialTheme.typography.body1
//                )
//                Text(
//                    text = "the rate is ".plus(PrintNumberHelper.printFloat(currency.rate)),
//                    style = MaterialTheme.typography.body2
//                )
//            }
//            TxtButton("", R.drawable.delete, onButtonClicked = {
//
//                isVisible = false
//            })
//        }
//    }
//}
