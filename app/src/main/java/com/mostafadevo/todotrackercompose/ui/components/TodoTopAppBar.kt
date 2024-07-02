package com.mostafadevo.todotrackercompose.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopAppBar(
    screen: String
) {
    TopAppBar(
        title = {
            when(screen){
                "Home" -> {
                    Text(text = screen, modifier = Modifier.padding(8.dp))
                }
                "Add" -> {
                    Text(text = screen, modifier = Modifier.padding(8.dp))
                }
                "Update"-> {
                    Text(text = screen, modifier = Modifier.padding(8.dp))
                }
            }
        },
        modifier = Modifier,
        navigationIcon = {
            when (screen) {
                "Home" -> {
                    Icon(
                        imageVector = Icons.Rounded.Home,
                        contentDescription = "home"
                        , modifier = Modifier.padding(8.dp)
                    )
                }
                "Add" -> {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "add"
                        , modifier = Modifier.padding(8.dp)
                    )
                }
                "Update"->{
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "update"
                        , modifier = Modifier.padding(8.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview
@Composable
private fun PreviewTodoTopAppBar() {
    TodoTopAppBar(
        screen = "update"
    )
}