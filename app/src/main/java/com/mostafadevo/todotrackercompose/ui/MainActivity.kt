package com.mostafadevo.todotrackercompose.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.HomeScreen
import com.mostafadevo.todotrackercompose.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.d("onCreate")
        setContent {
            AppTheme(
                darkTheme = true,
                dynamicColor = true
            ) {
                HomeScreen()
            }
        }
    }
}
