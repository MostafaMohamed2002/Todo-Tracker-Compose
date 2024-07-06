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
fun rememberTimer(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "timer",
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
                moveTo(16.417f, 4.542f)
                quadToRelative(-0.584f, 0f, -0.959f, -0.396f)
                reflectiveQuadToRelative(-0.375f, -0.938f)
                quadToRelative(0f, -0.541f, 0.375f, -0.937f)
                reflectiveQuadToRelative(0.959f, -0.396f)
                horizontalLineToRelative(7.208f)
                quadToRelative(0.5f, 0f, 0.896f, 0.396f)
                reflectiveQuadToRelative(0.396f, 0.937f)
                quadToRelative(0f, 0.542f, -0.396f, 0.938f)
                quadToRelative(-0.396f, 0.396f, -0.896f, 0.396f)
                close()
                moveTo(20f, 22.875f)
                quadToRelative(0.542f, 0f, 0.938f, -0.396f)
                quadToRelative(0.395f, -0.396f, 0.395f, -0.937f)
                verticalLineToRelative(-6.917f)
                quadToRelative(0f, -0.5f, -0.395f, -0.896f)
                quadToRelative(-0.396f, -0.396f, -0.938f, -0.396f)
                quadToRelative(-0.542f, 0f, -0.917f, 0.396f)
                reflectiveQuadToRelative(-0.375f, 0.896f)
                verticalLineToRelative(6.917f)
                quadToRelative(0f, 0.541f, 0.375f, 0.937f)
                reflectiveQuadToRelative(0.917f, 0.396f)
                close()
                moveToRelative(0f, 13.5f)
                quadToRelative(-3.042f, 0f, -5.729f, -1.167f)
                quadToRelative(-2.688f, -1.166f, -4.688f, -3.187f)
                quadToRelative(-2f, -2.021f, -3.166f, -4.709f)
                quadToRelative(-1.167f, -2.687f, -1.167f, -5.687f)
                quadToRelative(0f, -3.042f, 1.167f, -5.729f)
                quadToRelative(1.166f, -2.688f, 3.166f, -4.708f)
                quadToRelative(2f, -2.021f, 4.688f, -3.167f)
                quadTo(16.958f, 6.875f, 20f, 6.875f)
                quadToRelative(2.792f, 0f, 5.146f, 0.896f)
                reflectiveQuadToRelative(4.271f, 2.521f)
                lineToRelative(1.25f, -1.25f)
                quadToRelative(0.375f, -0.375f, 0.895f, -0.375f)
                quadToRelative(0.521f, 0f, 0.938f, 0.375f)
                quadToRelative(0.375f, 0.416f, 0.375f, 0.937f)
                quadToRelative(0f, 0.521f, -0.375f, 0.938f)
                lineToRelative(-1.25f, 1.208f)
                quadToRelative(1.5f, 1.75f, 2.5f, 4.063f)
                quadToRelative(1f, 2.312f, 1f, 5.437f)
                quadToRelative(0f, 3f, -1.167f, 5.687f)
                quadToRelative(-1.166f, 2.688f, -3.166f, 4.709f)
                quadToRelative(-2f, 2.021f, -4.688f, 3.187f)
                quadToRelative(-2.687f, 1.167f, -5.729f, 1.167f)
                close()
                moveToRelative(0f, -2.667f)
                quadToRelative(5.042f, 0f, 8.583f, -3.541f)
                quadToRelative(3.542f, -3.542f, 3.542f, -8.584f)
                quadToRelative(0f, -5.041f, -3.542f, -8.562f)
                quadTo(25.042f, 9.5f, 20f, 9.5f)
                reflectiveQuadToRelative(-8.583f, 3.542f)
                quadToRelative(-3.542f, 3.541f, -3.542f, 8.583f)
                reflectiveQuadToRelative(3.542f, 8.563f)
                quadToRelative(3.541f, 3.52f, 8.583f, 3.52f)
                close()
                moveToRelative(0f, -12.083f)
                close()
            }
        }.build()
    }
}