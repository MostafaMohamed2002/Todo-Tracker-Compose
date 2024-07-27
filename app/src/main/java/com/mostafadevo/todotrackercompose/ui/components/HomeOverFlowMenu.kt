package com.mostafadevo.todotrackercompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeOverFlowMenu(
  modifier: Modifier = Modifier,
  expanded: Boolean,
  onExpandedChange: (Boolean) -> Unit,
  onSortByTitle: () -> Unit,
  onSortByPriority: () -> Unit,
  onDeleteAllTodos: () -> Unit,
) {
  Row(
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Box {
      IconButton(onClick = { onExpandedChange(true) }) {
        Icon(
          imageVector = Icons.Default.MoreVert,
          contentDescription = "sort by",
          tint = MaterialTheme.colorScheme.onPrimary,
        )
      }

      DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandedChange(false) },
      ) {
        DropdownMenuItem(
          leadingIcon = {
            Icon(
              imageVector = rememberTitle(),
              contentDescription = "title",
            )
          },
          onClick = { onSortByTitle() },
          text = { Text("Title") },
        )
        DropdownMenuItem(
          leadingIcon = {
            Icon(
              imageVector = rememberPriorityHigh(),
              contentDescription = "priorityicon",
            )
          },
          onClick = { onSortByPriority() },
          text = { Text("Priority") },
        )
        DropdownMenuItem(
          text = {
            Text(text = "Delete All")
          },
          onClick = {
            onDeleteAllTodos()
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = "delete all",
            )
          },
        )
      }
    }
  }
}
