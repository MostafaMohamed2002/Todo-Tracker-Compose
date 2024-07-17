package com.mostafadevo.todotrackercompose.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    state: SettingsScreenUiState,
    onEvent: (SettingsScreenUiEvents) -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(bottom = paddingValues.calculateBottomPadding()),
        topBar = {
            LargeTopAppBar(
                title = { Text("Settings \uD83D\uDEE0\uFE0F") },
                scrollBehavior = scrollBehavior,
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                item {
                    Text(
                        "Appearance",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            Modifier.padding(16.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Text(
                                "Dark Mode",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                                modifier = Modifier.weight(1f)
                            )
                            Switch(checked = state.isDarkTheme,
                                onCheckedChange = {
                                    onEvent(SettingsScreenUiEvents.ToggleDarkTheme(it))
                                })
                        }
                    }
                }
                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            Modifier.padding(16.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Text(
                                "Dynamic Colors",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                                modifier = Modifier.weight(1f)
                            )
                            Switch(checked = state.isDynamicColors,
                                onCheckedChange = {
                                    onEvent(SettingsScreenUiEvents.ToggleDynamicColors(it))
                                })
                        }
                    }
                }
            }
        }
    )
}