package com.mostafadevo.todotrackercompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.mostafadevo.todotrackercompose.ui.BottomNavBar
import com.mostafadevo.todotrackercompose.ui.BottomNavigationNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
  val navController = rememberNavController()
  Scaffold(
    bottomBar = { BottomNavBar(navController = navController) },
  ) { paddingValues ->
    BottomNavigationNavGraph(navController = navController, paddingValues)
  }
}
