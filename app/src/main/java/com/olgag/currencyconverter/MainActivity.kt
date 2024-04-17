package com.olgag.currencyconverter

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.olgag.currencyconverter.model.CurrencyConverterViewModel
import com.olgag.currencyconverter.ui.theme.CurrencyconverterTheme
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.olgag.currencyconverter.api_service.CurrencyConverterRepository
import com.olgag.currencyconverter.api_service.FBHelper
import com.olgag.currencyconverter.api_service.OnFBConfigListener
import com.olgag.currencyconverter.components.*
import com.olgag.currencyconverter.model.Country
import com.olgag.currencyconverter.model.Currency
import com.olgag.currencyconverter.model.TabViewModelFactory
import com.olgag.currencyconverter.navigation.NavigationHost
import com.olgag.currencyconverter.screens.LoaderShimmerEffect
import com.olgag.currencyconverter.utils.InternetAvailable
import kotlinx.coroutines.*


var API_KEY: String? = null
var IS_INTERNEAT_AVAILABLE: Boolean = true
var COUNTRIES: List<Country>? = emptyList()
var DB_LAST_UPDATE: String? = null
var IS_DB_UPDATED: Boolean = true
var euroCurrency: Currency = Currency()

class MainActivity : ComponentActivity(), OnFBConfigListener {
    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val loader = remember {
                MutableTransitionState(true)
            }
            initApp(this, loader)
            CurrencyconverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    if (loader.targetState || isLoading) {
                        LoaderShimmerEffect()
                    } else {
                        val owner = LocalViewModelStoreOwner.current
                        owner?.let {
                            val viewModel: CurrencyConverterViewModel = viewModel(
                                it,
                                "CurrencyConverterViewModel",
                                TabViewModelFactory(
                                    LocalContext.current.applicationContext
                                            as Application
                                )
                            )
                            AppMainScreen(viewModel)
                        }
                    }
                }
            }
        }
    }

    private fun initApp(context: Context, loader: MutableTransitionState<Boolean>, ) {
        IS_INTERNEAT_AVAILABLE = InternetAvailable.isInternetAvailable(context)
        if (IS_INTERNEAT_AVAILABLE) {
            val fbConfig = FBHelper(context)
            fbConfig.setApiKey(loader, false)
        } else {
            loader.targetState = false
            isLoading = false
        }
    }

    override fun setApiKey(loader: MutableTransitionState<Boolean>, error: Boolean) {
        if (error) {
            loader.targetState = false
            isLoading = false
            IS_INTERNEAT_AVAILABLE = false
        } else {
            lifecycleScope.launch {
                val repository = CurrencyConverterRepository(this@MainActivity)
                COUNTRIES = repository.getCountries()
                loader.targetState = false
                isLoading = false
            }
        }
    }

    @Composable
    fun AppMainScreen(viewModel: CurrencyConverterViewModel) {
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colors.background) {
            var disableRoute: String by remember { mutableStateOf("") }
            var startDestination: String by remember { mutableStateOf(DrawerScreens.OnLineConverter.route) }
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
            if (!IS_INTERNEAT_AVAILABLE || COUNTRIES.isNullOrEmpty()) {
                startDestination = DrawerScreens.OffLineConverter.route
                disableRoute = DrawerScreens.OnLineConverter.route
            }
            ModalDrawer(
                drawerState = drawerState,
                gesturesEnabled = drawerState.isOpen,
                drawerContent = {
                    Drawer(
                        modifier = Modifier.background(MaterialTheme.colors.background),
                        disableRoute = disableRoute,
                        onDestinationClicked = { route ->
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            )
            {
                NavigationHost(navController, openDrawer, startDestination, viewModel)
            }
        }
    }
}
