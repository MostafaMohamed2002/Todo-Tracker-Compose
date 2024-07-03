package com.mostafadevo.todotrackercompose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

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
                    Icon(imageVector = rememberTitle(), contentDescription = "title")
                },
                onClick = { onSortByTitle() },
                text = { Text("Title") }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(imageVector = rememberPriorityHigh(), contentDescription = "priorityicon")
                },
                onClick = { onSortByPriority() },
                text = { Text("Priority") }
            )
        }
    }
}

@Composable
fun rememberPriorityHigh(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "priority_high",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20f, 34.625f)
                quadToRelative(-1.208f, 0f, -2.062f, -0.875f)
                quadToRelative(-0.855f, -0.875f, -0.855f, -2.083f)
                quadToRelative(0f, -1.167f, 0.875f, -2.021f)
                quadToRelative(0.875f, -0.854f, 2.042f, -0.854f)
                quadToRelative(1.208f, 0f, 2.062f, 0.854f)
                quadToRelative(0.855f, 0.854f, 0.855f, 2.062f)
                quadToRelative(0f, 1.167f, -0.855f, 2.042f)
                quadToRelative(-0.854f, 0.875f, -2.062f, 0.875f)
                close()
                moveToRelative(0f, -9.667f)
                quadToRelative(-1.208f, 0f, -2.042f, -0.854f)
                quadToRelative(-0.833f, -0.854f, -0.833f, -2.062f)
                verticalLineTo(8.125f)
                quadToRelative(0f, -1.208f, 0.833f, -2.042f)
                quadToRelative(0.834f, -0.833f, 2.042f, -0.833f)
                quadToRelative(1.208f, 0f, 2.042f, 0.833f)
                quadToRelative(0.833f, 0.834f, 0.833f, 2.042f)
                verticalLineToRelative(13.917f)
                quadToRelative(0f, 1.208f, -0.833f, 2.062f)
                quadToRelative(-0.834f, 0.854f, -2.042f, 0.854f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberTitle(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "title",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20f, 33.208f)
                quadToRelative(-0.875f, 0f, -1.5f, -0.604f)
                reflectiveQuadToRelative(-0.625f, -1.479f)
                verticalLineTo(11f)
                horizontalLineToRelative(-7.333f)
                quadToRelative(-0.875f, 0f, -1.48f, -0.625f)
                quadToRelative(-0.604f, -0.625f, -0.604f, -1.5f)
                reflectiveQuadToRelative(0.604f, -1.479f)
                quadToRelative(0.605f, -0.604f, 1.48f, -0.604f)
                horizontalLineToRelative(18.916f)
                quadToRelative(0.875f, 0f, 1.48f, 0.604f)
                quadToRelative(0.604f, 0.604f, 0.604f, 1.521f)
                quadToRelative(0f, 0.875f, -0.604f, 1.479f)
                quadToRelative(-0.605f, 0.604f, -1.48f, 0.604f)
                horizontalLineToRelative(-7.333f)
                verticalLineToRelative(20.125f)
                quadToRelative(0f, 0.875f, -0.625f, 1.479f)
                quadToRelative(-0.625f, 0.604f, -1.5f, 0.604f)
                close()
            }
        }.build()
    }
}