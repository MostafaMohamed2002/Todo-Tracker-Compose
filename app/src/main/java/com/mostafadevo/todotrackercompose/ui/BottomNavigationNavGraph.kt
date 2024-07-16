package com.mostafadevo.todotrackercompose.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mostafadevo.todotrackercompose.Utils.BottomNavigationItem
import com.mostafadevo.todotrackercompose.ui.screens.SettingScreen
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.HomeScreen
import com.mostafadevo.todotrackercompose.ui.screens.searchscreen.SearchScreen
import com.mostafadevo.todotrackercompose.ui.screens.searchscreen.SearchScreenViewModel

@Composable
fun BottomNavigationNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    val mViewModel: SearchScreenViewModel = hiltViewModel()
    val uiState by mViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        startDestination = BottomNavigationItem.Home.route,
        navController = navController
    ) {
        composable(BottomNavigationItem.Home.route) {
            HomeScreen(paddingValues = paddingValues)
        }
        composable(BottomNavigationItem.Setting.route) {
            SettingScreen()
        }
        composable(BottomNavigationItem.Search.route) {
            SearchScreen(
                navController = navController,
                onEvent = mViewModel::onEvent,
                state = uiState
            )
        }
    }
}