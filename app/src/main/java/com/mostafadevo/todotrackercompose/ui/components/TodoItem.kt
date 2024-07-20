package com.mostafadevo.todotrackercompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mostafadevo.todotrackercompose.data.local.Priority
import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.ui.theme.priorityHigh
import com.mostafadevo.todotrackercompose.ui.theme.priorityLow
import com.mostafadevo.todotrackercompose.ui.theme.priorityMedium
import com.mostafadevo.todotrackercompose.ui.theme.priorityUnspecified
import java.util.Date

@Composable
fun TodoItem(
  modifier: Modifier = Modifier,
  todo: Todo,
  onCheckedChange: (Boolean) -> Unit,
) {
  OutlinedCard(
    modifier =
    modifier
      .fillMaxWidth()
      .padding(16.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Column(
        modifier =
        Modifier
          .padding(16.dp)
          .weight(1f),
      ) {
        Text(
          text = todo.title,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (todo.description != null) {
          Text(text = todo.description)
          Spacer(modifier = Modifier.height(8.dp))
        }
        Text(
          text = "Priority: ${todo.priority}",
          color =
          when (todo.priority) {
            Priority.HIGH -> priorityHigh
            Priority.MEDIUM -> priorityMedium
            Priority.LOW -> priorityLow
            Priority.UNSPECIFIED -> priorityUnspecified
          },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Due: ${Date(todo.dueDateTime)}",
          style = MaterialTheme.typography.bodySmall,
        )
      }
      Column(
        modifier =
        Modifier
          .weight(0.2f)
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
      ) {
        Checkbox(
          checked = todo.isCompleted,
          onCheckedChange = {
            onCheckedChange(it)
          },
        )
      }
    }
  }
}

@Preview
@Composable
private fun TodoItemPreview() {
  TodoItem(
    todo =
    Todo(
      id = 1,
      title = "title",
      description = "this the description",
      priority = Priority.HIGH,
      dueDateTime = 1719886642580,
      isCompleted = false,
      isAlarmEnabled = false,
    ),
    onCheckedChange = {},
  )
}
