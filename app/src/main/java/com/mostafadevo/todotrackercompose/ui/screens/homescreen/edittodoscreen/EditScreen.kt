package com.mostafadevo.todotrackercompose.ui.screens.homescreen.edittodoscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.mostafadevo.todotrackercompose.Utils.toFormattedDateString
import com.mostafadevo.todotrackercompose.data.local.Priority
import com.mostafadevo.todotrackercompose.ui.components.rememberTimer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
  navController: NavHostController,
  uiState: EditScreenUiState,
  onEvent: (EditScreenEvents) -> Unit
) {
  //internal state
  val isPriorityMenuExpanded = remember { mutableStateOf(false) }
  var showDatePicker = remember { mutableStateOf(false) }
  var showTimePicker = remember { mutableStateOf(false) }
  val datePickerState =
    rememberDatePickerState(
      initialSelectedDateMillis = uiState.date,
    )
  Scaffold { innerpadding ->
    Column(
      modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(innerpadding)
    ) {
      //title
      OutlinedTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        value = uiState.title,
        onValueChange = {
          onEvent(EditScreenEvents.OnTitleChange(it))
        },
        label = { Text("Title") }
      )
      //description
      OutlinedTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        value = uiState.description ?: "",
        onValueChange = {
          onEvent(EditScreenEvents.OnDescriptionChange(it))
        },
        label = { Text("Description") }
      )
      // priority
      ExposedDropdownMenuBox(expanded = isPriorityMenuExpanded.value, onExpandedChange = {
        isPriorityMenuExpanded.value = it
      }) {
        OutlinedTextField(
          modifier =
          Modifier
            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            .fillMaxWidth()
            .padding(16.dp),
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPriorityMenuExpanded.value) },
          value = uiState.priority.name,
          onValueChange = {},
          readOnly = true,
          singleLine = true,
          label = { Text(text = "Priority") },
          colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
        )
        ExposedDropdownMenu(
          expanded = isPriorityMenuExpanded.value,
          onDismissRequest = { isPriorityMenuExpanded.value = false },
        ) {
          Priority.entries.forEach {
            DropdownMenuItem(
              text = { Text(text = it.name) },
              onClick = {
                isPriorityMenuExpanded.value = false
                onEvent(EditScreenEvents.OnPriorityChange(it))
              },
              contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
            )
          }
        }
      }
      // switch row
      Row(
        modifier = Modifier.padding(16.dp)
      ) {
        Text(
          modifier = Modifier.weight(1f),
          text = "Alarm Enabled ?",
        )
        // switch
        Switch(
          checked = uiState.isAlarmEnabled,
          onCheckedChange = {
            onEvent(EditScreenEvents.OnAlarmToggle(it))
          }
        )
      }
      // date picker
      AnimatedVisibility(uiState.isAlarmEnabled) {
        Column {
          OutlinedTextField(
            modifier =
            Modifier
              .fillMaxWidth()
              .padding(16.dp),
            value = uiState.date.toFormattedDateString(),
            onValueChange = {},
            readOnly = true,
            label = {
              Text(text = "Date")
            },
            trailingIcon = {
              Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "dateIcon",
                modifier =
                Modifier.clickable {
                  showDatePicker.value = true
                },
              )
            },
          )
          Spacer(modifier = Modifier.width(16.dp))
          OutlinedTextField(
            modifier =
            Modifier
              .fillMaxWidth()
              .padding(16.dp),
            value = "${uiState.time.hour}:${uiState.time.minute}",
            onValueChange = {},
            readOnly = true,
            label = {
              Text(text = "Time")
            },
            trailingIcon = {
              Icon(
                imageVector = rememberTimer(),
                contentDescription = "timeIcon",
                Modifier
                  .clickable {
                    showTimePicker.value = true
                  }
                  .size(30.dp),
              )
            },
          )
        }
      }
      //save button
      Button(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth(),
        onClick = {
          onEvent(EditScreenEvents.OnSaveClick)
          navController.popBackStack()
        }
      ) {
        Text(text = "Save")
      }
      //show date picker
      if (showDatePicker.value == true) {
        DatePickerDialog(
          onDismissRequest = {
            showDatePicker.value = false
          },
          confirmButton = {
            Button(onClick = {
              showDatePicker.value = false
              onEvent(EditScreenEvents.OnDateChange(datePickerState.selectedDateMillis!!))
            }) {
              Text(text = "Ok")
            }
          },
          dismissButton = {
            OutlinedButton(onClick = {
              showDatePicker.value = false
            }) {
              Text(text = "Cancel")
            }
          },
          properties =
          DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
          ),
        ) {
          DatePicker(
            state = datePickerState
          )
        }

      }
    }
  }
}
