package com.olgag.currencyconverter.components

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.model.CurrencyRow
import com.olgag.currencyconverter.utils.ConvertDate
import com.olgag.currencyconverter.utils.PrintNumberHelper

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedRateConversionByList(
    expanded: Boolean,
    calculateCurrency: CurrencyRow,
    onCloseClicked: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.secondary,
        modifier = Modifier
            .padding(5.dp)
            .clip(shape = MaterialTheme.shapes.medium)
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 1000
                                }
                            } else {
                                keyframes {
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 1000
                                }
                            }
                        }
            }
        ) { targetExpanded ->
            if (targetExpanded) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    IconButton(
                        onClick = {
                            onCloseClicked()
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.close),
                            contentDescription = null
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 40.dp, start = 20.dp, end = 20.dp)
                    ) {
                        Text(
                            text = "The rate from ",
                            style = MaterialTheme.typography.body2
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(calculateCurrency.imgFromResourceId),
                                contentDescription = null,
                                modifier = Modifier.width(36.dp)
                            )
                            Text(
                                text = calculateCurrency.currencyFromName,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "to ",
                                style = MaterialTheme.typography.body2
                            )
                            Image(
                                painter = painterResource(calculateCurrency.imgToResourceId),
                                contentDescription = null,
                                modifier = Modifier.width(36.dp)
                            )
                            Text(
                                text = calculateCurrency.currencyToName,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                        Text(
                            text = "For ${
                                ConvertDate.printDateFromStringAsString(
                                    calculateCurrency.currencyRangeDate
                                )
                            } , is:",
                            style = MaterialTheme.typography.body2
                        )
                        Text(
                            text = PrintNumberHelper.printFloat(calculateCurrency.rate),
                            style = MaterialTheme.typography.body2,
                        )
                        Column( modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                        ) {
                            RateConversion(rate = calculateCurrency.rate)
                        }
                    }
                }
            } else {
                Spacer( modifier = Modifier
                    .width(1.dp)
                    .height(1.dp))
            }
        }
    }
}
