package com.mostafadevo.todotrackercompose.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddTodoFloatingActionButton(
  onClick: () -> Unit,
  expanded: Boolean = false,
) {
  ExtendedFloatingActionButton(
    text = {
      Text(text = "Add Todo")
    },
    icon = {
      Icon(imageVector = Icons.Rounded.AddCircle, contentDescription = "add todo")
    },
    onClick = {
      onClick()
    },
    expanded = expanded,
  )
}

@Preview
@Composable
private fun PreviewAddTodoFloatingActionButton() {
  AddTodoFloatingActionButton({})
}
