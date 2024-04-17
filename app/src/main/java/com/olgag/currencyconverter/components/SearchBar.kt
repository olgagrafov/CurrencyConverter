package com.olgag.currencyconverter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.model.Country
import com.olgag.currencyconverter.model.Currency

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBar(list: List<Country>, setCurrency: (Currency) -> Unit, active: Boolean)  {
    var active: Boolean by rememberSaveable { mutableStateOf(active) }
    var text: String by rememberSaveable { mutableStateOf("") }
    Box(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .semantics { isContainer = true }
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            DockedSearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                query = text,
                onQueryChange = { text = it },
                onSearch ={ active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(
                    text = stringResource(id = R.string.type_currency_name),
                    style = MaterialTheme.typography.h6) },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = null)
                },
                trailingIcon = {
                    Icon(
                        Icons.Rounded.Clear,
                        contentDescription = null,
                        Modifier.clickable{ text = ""})
                },
            ) {
                val filteredList = if (text.isEmpty()) {
                    list
                } else {
                    list.filter { it.name.contains(text, ignoreCase = true) || it.currencyName.contains(text, ignoreCase = true)}
                }
                LazyColumn()
                {
                    items(filteredList) { it ->
                        ListItem(
                            modifier = Modifier.clickable {
                                //text = it.currencyName
                                active = false
                                setCurrency(Currency(it.currencyName, it.currencyId, it.resourceId))
                            },
                            headlineContent = {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.h2
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = it.currencyName,
                                    style = MaterialTheme.typography.h3
                                )
                            },
                            trailingContent = {
                                Image(
                                    painter =  painterResource(it.resourceId),
                                    contentDescription = null,
                                    modifier = Modifier.width(36.dp))
                            },
                        )
                    }
                }
            }
        }
    }
}