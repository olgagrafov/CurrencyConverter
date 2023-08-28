package com.olgag.currencyconverter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.olgag.currencyconverter.components.DrawerScreens
import com.olgag.currencyconverter.model.Currency
import com.olgag.currencyconverter.model.CurrencyConverterViewModel
import com.olgag.currencyconverter.screens.*
import kotlinx.coroutines.Job


@Composable
fun NavigationHost(
    navController: NavHostController,
    openDrawer: () -> Job,
    startDestination: String,
    viewModel: CurrencyConverterViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("${DrawerScreens.OnLineConverter.route}?currencies={currencies}",
           arguments = listOf(navArgument("currencies") { defaultValue = "" }))
        { backStackEntry ->
            val gson: Gson = GsonBuilder().create()
            val currenciesJson = backStackEntry.arguments?.getString("currencies")
            val type = object : TypeToken<List<Currency>>() {}.type
            val currenciesList = gson.fromJson<List<Currency>>(currenciesJson, type)
            OnLineConverter(
                openDrawer = { openDrawer() },
                viewModel = viewModel,
                currencies = (currenciesList ?: emptyList())
            )
        }
        composable(DrawerScreens.OffLineConverter.route) {
            OffLineConverter(
                openDrawer = { openDrawer() },
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(DrawerScreens.About.route) {
            About(
                navController = navController
            )
        }
    }
}
