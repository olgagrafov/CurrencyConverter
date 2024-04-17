package com.olgag.currencyconverter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.olgag.currencyconverter.model.TabViewModel
import com.olgag.currencyconverter.screens.ConversionScreen
import com.olgag.currencyconverter.utils.tabs

@Composable
fun TabLayout(viewModel: TabViewModel) {
    val tabIndex = viewModel.tabIndex.observeAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex.value!!) {
            tabs.forEachIndexed { index, tab ->
                Tab(text = { Text(stringResource(id = tab.titleStringRes), style = MaterialTheme.typography.subtitle1)},
                    selected = tabIndex.value!! == index,
                    onClick = { viewModel.updateTabIndex(index) },
                    icon = { Icon(painter = painterResource(tab.iconDrawableResource), contentDescription = null) }
                )
            }
        }
        ConversionScreen(viewModel = viewModel)
    }
}
