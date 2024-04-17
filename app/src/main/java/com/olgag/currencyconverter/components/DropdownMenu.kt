package com.olgag.currencyconverter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.olgag.currencyconverter.R


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DropdownMenu(
        dataList: MutableMap<String, Float>,
        title: String,
        active: Boolean,
        setActive: (Boolean) -> Unit,
        text: String,
        setNameOfConversion: (String) -> Unit,
        setCoefficientOfConversion: (Float) -> Unit
    )
{
    Box(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .zIndex(1f)
                    .fillMaxWidth()
            ) {
                DockedSearchBar(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    query = text,
                    onQueryChange = { setNameOfConversion(it) },
                    onSearch ={ setActive(false) },
                    active = active,
                    onActiveChange = { setActive(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.type_kind, title),
                            style = MaterialTheme.typography.h6
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Rounded.Search,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Rounded.Clear,
                            contentDescription = null,
                            Modifier.clickable { setNameOfConversion("") })
                    },
                ) {
                    val filteredList = if (text.isEmpty()) {
                        dataList
                    } else {
                        dataList.filter { it.key.contains(text, ignoreCase = true) }
                    }
                    LazyColumn()
                    {
                        items(filteredList.toList()) { it ->
                            ListItem(
                                modifier = Modifier.clickable {
                                    setActive(false)
                                    setNameOfConversion(it.first)
                                    setCoefficientOfConversion(it.second)
                                },
                                headlineContent = {
                                    Text(
                                        text = it.first,
                                        style = MaterialTheme.typography.h2
                                    )
                                },
                                trailingContent = {
                                    Text(
                                        text = it.second.toString(),
                                        style = MaterialTheme.typography.h2
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
