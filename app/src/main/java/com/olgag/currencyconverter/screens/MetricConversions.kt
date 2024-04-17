package com.olgag.currencyconverter.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.components.TabLayout
import com.olgag.currencyconverter.components.TopBar
import com.olgag.currencyconverter.model.TabViewModel

@Composable
fun MetricConversions(
    openDrawer: () -> Unit
) {
    val application: Application =  LocalContext.current.applicationContext
            as Application
    val viewModel= TabViewModel(application)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.background,
                        MaterialTheme.colors.secondary
                    )
                )
            )
    ) {
        TopBar(
            title = stringResource(id = R.string.metric_conversions),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer()
            }
        )
        TabLayout(viewModel)
   }
}