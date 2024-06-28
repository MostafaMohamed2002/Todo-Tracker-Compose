package com.mostafadevo.todotrackercompose.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import com.mostafadevo.todotrackercompose.ui.theme.AppTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.d("onCreate")
        setContent {
            AppTheme() {
                Scaffold() { padding ->

                }
            }
        }
    }
}
