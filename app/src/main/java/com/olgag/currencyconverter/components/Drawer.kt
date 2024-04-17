package com.olgag.currencyconverter.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.olgag.currencyconverter.R
import androidx.compose.ui.unit.dp


sealed class DrawerScreens(@StringRes val title: Int, val route: String, @DrawableRes val drawableResource: Int) {
    object OnLineConverter: DrawerScreens(R.string.currency_rates, "online_converter", R.drawable.internet_available)
    object OffLineConverter: DrawerScreens(R.string.saved_conversion, "offline_converter", R.drawable.internet_not_available)
    object MetricConversions: DrawerScreens(R.string.metric_conversions, "metric_conversions", R.drawable.metric)
    object About: DrawerScreens( R.string.about, "about", R.drawable.about)
}

private val screens = listOf(
    DrawerScreens.OnLineConverter,
    DrawerScreens.OffLineConverter,
    DrawerScreens.MetricConversions,
    DrawerScreens.About
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit,
    disableRoute: String,
    ) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_drawer),
            contentDescription = "",
            modifier = Modifier.width(150.dp).height(150.dp).align(alignment = Alignment.CenterHorizontally)
        )
        screens.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            if(screen.route == disableRoute) {
                TxtButton(
                    stringResource(id = screen.title).plus( stringResource(id = R.string.doesnt_work_currently)),
                    screen.drawableResource,
                    onButtonClicked = { },
                    rightIcon = true,
                    isEnabled = false
                )
            }
            else {
                TxtButton(
                    stringResource(id = screen.title),
                    screen.drawableResource,
                    onButtonClicked = { onDestinationClicked( screen.route ) },
                    rightIcon = true,
                )
            }
        }
    }
}
