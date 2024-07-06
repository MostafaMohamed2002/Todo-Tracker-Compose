package com.mostafadevo.todotrackercompose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeOverFlowMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onSortByTitle: () -> Unit,
    onSortByPriority: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { onExpandedChange(true) }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "sort by",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = rememberTitle(),
                        contentDescription = "title",
                        modifier = Modifier.size(16.dp)
                    )
                },
                onClick = { onSortByTitle() },
                text = { Text("Title", fontSize = 16.sp) }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = rememberPriorityHigh(),
                        contentDescription = "priorityicon",
                        modifier = Modifier.size(16.dp)
                    )
                },
                onClick = { onSortByPriority() },
                text = { Text("Priority", fontSize = 16.sp) }
            )
        }
    }
}

