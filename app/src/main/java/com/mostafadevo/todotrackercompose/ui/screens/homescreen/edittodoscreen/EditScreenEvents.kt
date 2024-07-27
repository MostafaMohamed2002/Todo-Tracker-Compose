package com.mostafadevo.todotrackercompose.ui.screens.homescreen.edittodoscreen

import com.mostafadevo.todotrackercompose.data.local.Priority

sealed class EditScreenEvents {
  data class OnTitleChange(val title: String) : EditScreenEvents()
  data class OnDescriptionChange(val description: String) : EditScreenEvents()
  data class OnPriorityChange(val priority: Priority) : EditScreenEvents()
  data class OnDateChange(val date: Long) : EditScreenEvents()
  data class OnTimeChange(val time: Long) : EditScreenEvents()
  data class OnAlarmToggle(val isAlarmEnabled: Boolean) : EditScreenEvents()
  object OnSaveClick : EditScreenEvents()
}
