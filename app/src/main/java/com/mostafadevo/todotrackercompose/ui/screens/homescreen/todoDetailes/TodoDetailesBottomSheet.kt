package com.mostafadevo.todotrackercompose.ui.screens.homescreen.todoDetailes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.mostafadevo.todotrackercompose.Utils.toFormattedDateString
import com.mostafadevo.todotrackercompose.data.local.Priority
import com.mostafadevo.todotrackercompose.ui.components.TimePickerDialog
import com.mostafadevo.todotrackercompose.ui.components.rememberTimer
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.BottomSheetUiEventsFromViewModel
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.HomeViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailesBottomSheet(
  modifier: Modifier = Modifier,
  state: TodoDetailesBottomSheetUiState,
  onDismiss: () -> Unit,
  onEvent: (TodoDetailesBottomSheetUiEvent) -> Unit,
  mHomeViewModel: HomeViewModel = hiltViewModel(),
) {
  var showDatePicker by remember { mutableStateOf(false) }
  var showTimePicker by remember { mutableStateOf(false) }
  val datePickerState =
    rememberDatePickerState(
      initialSelectedDateMillis = state.date,
    )
  val isReadOnly = state.isCompleted || state.isEditMode.not()
  var isPriorityMenuExpanded by remember { mutableStateOf(false) }
  val sheetState =
    rememberModalBottomSheetState(
      skipPartiallyExpanded = false,
    )

  val uiEvents = mHomeViewModel.bottomSheetUiEventsFromViewModel
  LaunchedEffect(key1 = Unit) {
    uiEvents.collect {
      when (it) {
        BottomSheetUiEventsFromViewModel.Close -> {
          sheetState.hide()
          onDismiss()
        }
      }
    }
  }

  val scope = rememberCoroutineScope()
  ModalBottomSheet(
    modifier = Modifier.fillMaxHeight(),
    onDismissRequest = { onDismiss() },
    sheetState = sheetState,
    tonalElevation = 8.dp,
  ) {
    Row {
      Text(
        text = "Todo Detailes :",
        modifier = Modifier.padding(16.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
      )
      Spacer(modifier = Modifier.weight(1f))
      // delete icon
      IconButton(onClick = {
        Timber.d("Attempting to hide sheet")
        scope.launch {
          sheetState.hide()
          onDismiss()
        }
        Timber.d("Sheet should be hidden now")
        onEvent(TodoDetailesBottomSheetUiEvent.onDeleted)
      }) {
        Icon(imageVector = Icons.Default.Delete, contentDescription = "delete todo")
      }
      // toggle edit mode icon
      IconButton(onClick = {
        onEvent(TodoDetailesBottomSheetUiEvent.ToggleEditMode)
      }) {
        Icon(
          imageVector =
          if (state.isEditMode &&
            state.isCompleted.not()
          ) {
            Icons.Default.Done
          } else {
            Icons.Default.Edit
          },
          contentDescription = "",
        )
      }
    }
    OutlinedTextField(
      value = state.title,
      onValueChange = { onEvent(TodoDetailesBottomSheetUiEvent.onTitleChange(it)) },
      label = { Text(text = "Title") },
      modifier =
      Modifier
        .fillMaxWidth()
        .padding(16.dp),
      readOnly = isReadOnly,
    )

    OutlinedTextField(
      value = state.description,
      onValueChange = { onEvent(TodoDetailesBottomSheetUiEvent.onDescriptionChange(it)) },
      label = { Text(text = "Description") },
      modifier =
      Modifier
        .fillMaxWidth()
        .padding(16.dp),
      readOnly = isReadOnly,
    )

    if (state.isEditMode == true && state.isCompleted.not()) {
      // priority exposed menu
      ExposedDropdownMenuBox(expanded = isPriorityMenuExpanded, onExpandedChange = {
        isPriorityMenuExpanded = it
      }) {
        OutlinedTextField(
          modifier =
          Modifier
            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            .fillMaxWidth()
            .padding(16.dp),
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPriorityMenuExpanded) },
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
              onClick = { onEvent(TodoDetailesBottomSheetUiEvent.onPriorityChange(it)) },
              contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
            )
          }
        }
      }
      if (state.isAlarmEnabled) {
        OutlinedTextField(
          modifier =
          Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
          modifier =
          Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
              Modifier
                .clickable {
                  showTimePicker = true
                }
                .size(30.dp),
            )
          },
        )
      }
    } else {
      // when mode is read only
      OutlinedTextField(
        value = state.priority.name,
        onValueChange = { },
        label = { Text(text = "Priority") },
        modifier =
        Modifier
          .fillMaxWidth()
          .padding(16.dp),
        readOnly = true,
      )
      OutlinedTextField(
        value = state.date.toFormattedDateString(),
        onValueChange = { /*TODO*/ },
        label = { Text(text = "Due Date") },
        modifier =
        Modifier
          .fillMaxWidth()
          .padding(16.dp),
        readOnly = true,
        trailingIcon = {
          Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "",
          )
        },
      )

      OutlinedTextField(
        value = state.time.toString(),
        onValueChange = { /*TODO*/ },
        label = { Text(text = "Due Time") },
        modifier =
        Modifier
          .fillMaxWidth()
          .padding(16.dp),
        readOnly = true,
        trailingIcon = {
          Icon(
            imageVector = rememberTimer(),
            contentDescription = "",
            modifier = Modifier.size(30.dp),
          )
        },
      )
    }
  }
  if (showTimePicker) {
    TimePickerDialog(
      onCancel = { showTimePicker = false },
      onConfirm = {
        showTimePicker = false
        onEvent(TodoDetailesBottomSheetUiEvent.onTimeChange(it))
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
          onEvent(TodoDetailesBottomSheetUiEvent.onDateChange(datePickerState.selectedDateMillis!!))
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
