package com.mostafadevo.todotrackercompose.ui.screens.homescreen

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mostafadevo.todotrackercompose.Utils.Screens
import com.mostafadevo.todotrackercompose.data.local.Priority
import com.mostafadevo.todotrackercompose.ui.components.AddTodoFloatingActionButton
import com.mostafadevo.todotrackercompose.ui.components.TodoTopAppBar
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.AddTodo.AddDialog
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.todoDetailes.TodoDetailesBottomSheet
import com.mostafadevo.todotrackercompose.ui.theme.priorityHigh
import com.mostafadevo.todotrackercompose.ui.theme.priorityLow
import com.mostafadevo.todotrackercompose.ui.theme.priorityMedium
import com.mostafadevo.todotrackercompose.ui.theme.priorityUnspecified
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun HomeScreen(
    mViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by mViewModel.homeUiState.collectAsState()
    val addTodoDialogUiState by mViewModel.addTodoDialogUiState.collectAsState()
    val todoDetailesBottomSheetUiState by mViewModel.todoDetailesBottomSheetUiState.collectAsState()
    val lazyColumnState = rememberLazyListState()
    val isScrolledDown by remember { derivedStateOf { lazyColumnState.firstVisibleItemIndex > 0 } }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            AddTodoFloatingActionButton(onClick = {
                mViewModel.onEvent(
                    HomeScreenUiEvent.AddTodoButton
                )
            }, expanded = !isScrolledDown)
        },
        topBar = {
            TodoTopAppBar(
                scrollBehavior = scrollBehavior,
                screen = "Home",
                expanded = uiState.isMenuExpanded,
                onExpandedChange = {
                    mViewModel.onEvent(HomeScreenUiEvent.ToggleSortingMenu(it))
                },
                onSortByTitle = {
                    mViewModel.onEvent(HomeScreenUiEvent.SortTodos(SortingOptions.BY_TITLE))
                }, onSortByPriority = {
                    mViewModel.onEvent(HomeScreenUiEvent.SortTodos(SortingOptions.BY_PRIORITY))
                }, onDeleteAllTodos = {
                    mViewModel.onEvent(HomeScreenUiEvent.onDeleteAllTodos)
                }, onNavigateToSearchScreen = { navController.navigate(Screens.SEARCH_SCREEN) })
        }
    ) { paddingValues ->
        val options = listOf("To-Do", "Done")
        if (uiState.isAddTodoDialogOpen) {
            AddDialog(
                state = addTodoDialogUiState,
                onEvent = mViewModel::onDialogEvent
            )
        }
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = lazyColumnState
        ) {
            item {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            selected = index == uiState.selectedSegmentIndex,
                            onClick = {
                                mViewModel.onEvent(HomeScreenUiEvent.SelectSegment(selctedSegment = index))
                            },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size,
                            )
                        ) {
                            Text(text = "$label")
                        }
                    }
                }
            }
            items(items = uiState.todos, key = { it.id }) { todo ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .animateItem(
                            //customized animations
                            fadeInSpec = null,
                            fadeOutSpec = null,
                            placementSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                        .clickable {
                            Timber.d("Clicked on todo item: ${todo.id}")
                            mViewModel.onEvent(HomeScreenUiEvent.onTodoClick(todo))
                        }


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
                            Text(
                                text = "Priority: ${todo.priority}",
                                color = when (todo.priority) {
                                    Priority.HIGH -> priorityHigh
                                    Priority.MEDIUM -> priorityMedium
                                    Priority.LOW -> priorityLow
                                    Priority.UNSPECIFIED -> priorityUnspecified
                                }
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

        if (uiState.isTodoDetailesBottomSheetEnabled) {
            uiState.currentShownTodo?.let {
                TodoDetailesBottomSheet(
                    state = todoDetailesBottomSheetUiState,
                    onDismiss = {
                        mViewModel.onEvent(HomeScreenUiEvent.onDismissTodoDetailesBottomSheet)
                    },
                    onEvent = mViewModel::onBottomSheetEvent
                )
            }
        }
        if (uiState.todos.isEmpty() && uiState.selectedSegmentIndex == 0) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No To-Do items found",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else if (uiState.todos.isEmpty() && uiState.selectedSegmentIndex == 1) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Done items found",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}