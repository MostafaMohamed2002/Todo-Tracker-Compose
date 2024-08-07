package com.mostafadevo.todotrackercompose.ui.screens.homescreen.AddTodo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mostafadevo.todotrackercompose.Utils.toFormattedDateString
import com.mostafadevo.todotrackercompose.data.local.Priority
import com.mostafadevo.todotrackercompose.ui.components.TimePickerDialog
import com.mostafadevo.todotrackercompose.ui.components.rememberTimer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialog(
  state: AddTodoDialogUiState,
  onEvent: (AddTodoDialogUiEvents) -> Unit,
) {
  val focusRequester = remember { FocusRequester() } // Initialize FocusRequester
  var showDatePicker by remember { mutableStateOf(false) }
  var showTimePicker by remember { mutableStateOf(false) }
  var isPriorityMenuExpanded by remember {
    mutableStateOf(false)
  }
  val datePickerState =
    rememberDatePickerState(
      initialSelectedDateMillis = state.date,
    )

  Dialog(onDismissRequest = {
    onEvent(AddTodoDialogUiEvents.Close)
  }) {
    Surface(
      shape = MaterialTheme.shapes.large,
      tonalElevation = 8.dp,
    ) {
      Column(
        modifier = Modifier
          .padding(16.dp)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(text = "Add Todo", style = MaterialTheme.typography.bodySmall)
        OutlinedTextField(
          value = state.title,
          onValueChange = {
            onEvent(AddTodoDialogUiEvents.OnTitleChange(it))
          },
          label = { Text("Title") },
          modifier =
          Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        )
        LaunchedEffect(Unit) {
          focusRequester.requestFocus() // Request focus during composition
        }
        OutlinedTextField(
          value = state.description,
          onValueChange = {
            onEvent(AddTodoDialogUiEvents.OnDescriptionChange(it))
          },
          label = { Text("Description") },
          modifier = Modifier.fillMaxWidth(),
        )
        // priority exposed menu
        ExposedDropdownMenuBox(expanded = isPriorityMenuExpanded, onExpandedChange = {
          isPriorityMenuExpanded = it
        }) {
          OutlinedTextField(
            modifier =
            Modifier
              .menuAnchor(MenuAnchorType.PrimaryNotEditable)
              .fillMaxWidth(),
            trailingIcon = {
              ExposedDropdownMenuDefaults.TrailingIcon(
                expanded = isPriorityMenuExpanded,
              )
            },
            value = state.priority.name,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text(text = "Priority") },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
          )
          ExposedDropdownMenu(
            expanded = isPriorityMenuExpanded,
            onDismissRequest = { isPriorityMenuExpanded = false },
          ) {
            Priority.entries.forEach {
              DropdownMenuItem(
                text = { Text(text = it.name) },
                onClick = {
                  isPriorityMenuExpanded = false
                  onEvent(AddTodoDialogUiEvents.OnPriorityChange(it))
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
              )
            }
          }
        }
        // row for date , time buttons to show date picker and time picker
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = "Set Alarm ?",
            modifier = Modifier,
          )
          Spacer(modifier = Modifier.weight(1f))
          Switch(
            modifier = Modifier,
            checked = state.isAlarmEnabled,
            onCheckedChange = { onEvent(AddTodoDialogUiEvents.OnSetAlarmChange(it)) },
          )
        }
        AnimatedVisibility(state.isAlarmEnabled) {
          OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "${state.time.hour}:${state.time.minute}",
            onValueChange = {},
            readOnly = true,
            label = {
              Text(text = "Time")
            },
            trailingIcon = {
              Icon(
                imageVector = rememberTimer(),
                contentDescription = "timeIcon",
                Modifier.clickable {
                  showTimePicker = true
                },
              )
            },
          )
          Spacer(modifier = Modifier.width(16.dp))
        }
        AnimatedVisibility(state.isAlarmEnabled) {
          OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.date.toFormattedDateString(),
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
                  showDatePicker = true
                },
              )
            },
          )
        }

        // close and save buttons
        Row {
          OutlinedButton(modifier = Modifier.weight(1f), onClick = {
            onEvent(AddTodoDialogUiEvents.Close)
          }) {
            Text(text = "Close")
          }
          Spacer(modifier = Modifier.width(16.dp))
          Button(modifier = Modifier.weight(1f), onClick = {
            onEvent(AddTodoDialogUiEvents.AddTodo)
          }) {
            Text(text = "Add")
          }
        }
      }
    }
    if (showTimePicker) {
      TimePickerDialog(
        onCancel = { showTimePicker = false },
        onConfirm = {
          showTimePicker = false
          onEvent(AddTodoDialogUiEvents.OnTimeChange(it))
        },
        initial = state.time,
      )
    }

    if (showDatePicker) {
      DatePickerDialog(
        onDismissRequest = {
          showDatePicker = false
        },
        confirmButton = {
          Button(onClick = {
            showDatePicker = false
            onEvent(AddTodoDialogUiEvents.OnDateChange(datePickerState.selectedDateMillis!!))
          }) {
            Text(text = "Ok")
          }
        },
        dismissButton = {
          OutlinedButton(onClick = {
            showDatePicker = false
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
        DatePicker(state = datePickerState)
      }
    }
  }
}
