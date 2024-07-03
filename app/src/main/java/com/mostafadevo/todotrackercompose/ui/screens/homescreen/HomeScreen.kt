package com.mostafadevo.todotrackercompose.ui.screens.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mostafadevo.todotrackercompose.data.local.Priority
import com.mostafadevo.todotrackercompose.ui.components.AddTodoFloatingActionButton
import com.mostafadevo.todotrackercompose.ui.components.TodoTopAppBar
import com.mostafadevo.todotrackercompose.ui.theme.priorityHigh
import com.mostafadevo.todotrackercompose.ui.theme.priorityLow
import com.mostafadevo.todotrackercompose.ui.theme.priorityMedium
import com.mostafadevo.todotrackercompose.ui.theme.priorityUnspecified
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    mViewModel: HomeViewModel = hiltViewModel()
) {
    val uistate by mViewModel.uiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            AddTodoFloatingActionButton(onClick = {
                mViewModel.onEvent(
                    HomeScreenUiEvent.AddTodo
                )
            })
        },
        topBar = {
            TodoTopAppBar(
                screen = "Home",
                expanded = uistate.isMenuExpanded,
                onExpandedChange = {
                    mViewModel.onEvent(HomeScreenUiEvent.ToggleSortingMenu(it))
                },
                onSortByTitle = {
                    mViewModel.onEvent(HomeScreenUiEvent.SortTodos(SortingOptions.BY_TITLE))
                }, onSortByPriority = {
                    mViewModel.onEvent(HomeScreenUiEvent.SortTodos(SortingOptions.BY_PRIORITY))
                })
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(items = uistate.todos, key = { it.id }) { todo ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .animateItemPlacement()


                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = todo.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (todo.description != null) {
                                Text(text = todo.description, maxLines = 1)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            Text(
                                text = "Priority: ${todo.priority}",
                                color = when (todo.priority) {
                                    Priority.HIGH -> priorityHigh
                                    Priority.MEDIUM -> priorityMedium
                                    Priority.LOW -> priorityLow
                                    Priority.UNSPECIFIED -> priorityUnspecified
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Due: ${Date(todo.dueDateTime)}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(0.2f)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Checkbox(
                                checked = todo.isCompleted,
                                onCheckedChange = {
                                    mViewModel.onEvent(HomeScreenUiEvent.onCheckTodo(todo, it))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}