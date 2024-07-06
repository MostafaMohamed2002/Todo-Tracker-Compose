package com.mostafadevo.todotrackercompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


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