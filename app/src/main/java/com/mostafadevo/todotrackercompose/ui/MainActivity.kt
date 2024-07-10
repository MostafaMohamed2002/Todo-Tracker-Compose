package com.mostafadevo.todotrackercompose.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.HomeScreen
import com.mostafadevo.todotrackercompose.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.d("onCreate")
        setContent {
            AppTheme{
                val context = LocalContext.current
                val showDialog = rememberSaveable { mutableStateOf(false) }


                // Check if the app can schedule exact alarms
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val alarmManager =
                            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        if (!alarmManager.canScheduleExactAlarms()) {
                            showDialog.value = true
                        }
                    }
                    // Check if permission is already granted
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // Request the permission
                        // from android 13 you need to request permission for post notification
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }

                if (showDialog.value) {
                    ExactAlarmsPermissionDialog(
                        onDismiss = { showDialog.value = false },
                        onGoToSettings = {
                            startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                            showDialog.value = false
                        }
                    )
                }

                HomeScreen()
            }
        }
    }

    // Register the permission launcher
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted, handle the notification logic
            } else {
                // Permission is denied, handle accordingly
            }
        }
}

@Composable
fun ExactAlarmsPermissionDialog(onDismiss: () -> Unit, onGoToSettings: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Exact Alarms Permission Needed") },
        text = { Text("This app requires permission to schedule exact alarms. Please enable it in the settings.") },
        confirmButton = {
            Button(onClick = onGoToSettings) {
                Text("Go to Settings")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
