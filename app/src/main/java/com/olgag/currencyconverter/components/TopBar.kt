package com.olgag.currencyconverter.components

import android.app.Activity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext

@Composable
fun TopBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit) {
    val context = LocalContext.current as? Activity
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h1
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() } ) {
                Icon(buttonIcon, contentDescription = "", tint = MaterialTheme.colors.background)
            }
        },
        actions = {
            IconButton(onClick = {   context?.finish() }) {
                Icon(Icons.Rounded.ExitToApp, contentDescription = "", tint = MaterialTheme.colors.background)
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant
    )
}