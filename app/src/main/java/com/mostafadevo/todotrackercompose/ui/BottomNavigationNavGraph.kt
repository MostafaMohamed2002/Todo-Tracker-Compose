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
import com.mostafadevo.todotrackercompose.Utils.Screens
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.HomeScreen
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.edittodoscreen.EditScreen
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.edittodoscreen.SharedEditViewModel
import com.mostafadevo.todotrackercompose.ui.screens.searchscreen.SearchScreen
import com.mostafadevo.todotrackercompose.ui.screens.searchscreen.SearchScreenViewModel
import com.mostafadevo.todotrackercompose.ui.screens.settings.SettingsScreen
import com.mostafadevo.todotrackercompose.ui.screens.settings.SettingsScreenViewModel
import timber.log.Timber

@Composable
fun BottomNavigationNavGraph(
  navController: NavHostController,
  paddingValues: PaddingValues,
) {
  val sharedEditViewModel: SharedEditViewModel = hiltViewModel()

  NavHost(
    startDestination = BottomNavigationItem.Home.route,
    navController = navController,
  ) {
    composable(BottomNavigationItem.Home.route) {
      HomeScreen(
        paddingValues = paddingValues,
        navController = navController,
        sharedEditViewModel = sharedEditViewModel,
      )
    }
    composable(BottomNavigationItem.Setting.route) {
      val mSettingScreenViewModel: SettingsScreenViewModel = hiltViewModel()
      val mSettingScreenUiState by mSettingScreenViewModel.uiState.collectAsStateWithLifecycle()
      Timber.d("BottomNavigationNavGraph: mSettingScreenUiState: $mSettingScreenUiState")
      SettingsScreen(
        paddingValues = paddingValues,
        state = mSettingScreenUiState,
        onEvent = mSettingScreenViewModel::onEvent,
      )
    }
    composable(BottomNavigationItem.Search.route) {
      val mViewModel: SearchScreenViewModel = hiltViewModel()
      val uiState by mViewModel.uiState.collectAsStateWithLifecycle()
      SearchScreen(
        navController = navController,
        onEvent = mViewModel::onEvent,
        state = uiState,
      )
    }
    composable(Screens.EditScreen) {
      val uiState by sharedEditViewModel.uiState.collectAsStateWithLifecycle()
      EditScreen(navController, uiState, sharedEditViewModel::onEvent)
    }
  }
}
