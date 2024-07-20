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
fun rememberTitle(): ImageVector =
  remember {
    ImageVector
      .Builder(
        name = "title",
        defaultWidth = 40.0.dp,
        defaultHeight = 40.0.dp,
        viewportWidth = 40.0f,
        viewportHeight = 40.0f,
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
          pathFillType = PathFillType.NonZero,
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
