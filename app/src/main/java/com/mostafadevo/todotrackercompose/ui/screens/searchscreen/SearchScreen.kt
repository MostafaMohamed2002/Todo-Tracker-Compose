package com.mostafadevo.todotrackercompose.ui.screens.searchscreen

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mostafadevo.todotrackercompose.Utils.Screens
import com.mostafadevo.todotrackercompose.ui.components.TodoItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: SearchScreenUiState,
    onEvent: (SearchScreenUiEvents) -> Unit
) {

    var isExpanded by remember {
        mutableStateOf(true)
    }
    val focusRequester = remember { FocusRequester() } // Initialize FocusRequester
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.focusRequester(focusRequester),
                query = state.query,
                onQueryChange = { onEvent(SearchScreenUiEvents.onQueryChange(it)) },
                onSearch = { onEvent(SearchScreenUiEvents.onSearch) },
                expanded = isExpanded,
                onExpandedChange = { }, placeholder = {
                    Text(text = "Type to search")
                }, leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                navController.popBackStack(Screens.HOME_SCREEN, false)
                            }

                    )
                }
            )
        },
        expanded = isExpanded,
        onExpandedChange = { }
    ) {
        LazyColumn {
            state.todos.let {
                items(it!!, key = { it.id }) {
                    TodoItem(
                        todo = it,
                        onCheckedChange = {},
                        modifier = Modifier.animateItem(
                            fadeInSpec = null,
                            fadeOutSpec = null,
                            placementSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    )
                }
            }
        }

    }
}

