/*
 * Copyright 2024 by Patryk Goworowski and Patrick Michalik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.patrykandpatrick.vico.compose.chart.layer

import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.layer.CartesianLayer
import com.patrykandpatrick.vico.core.chart.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.chart.layer.LineCartesianLayer.LineSpec
import com.patrykandpatrick.vico.core.chart.values.AxisValueOverrider
import com.patrykandpatrick.vico.core.component.Component
import com.patrykandpatrick.vico.core.component.shape.shader.ColorShader
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShader
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.component.shape.shader.TopBottomShader
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.VerticalPosition
import com.patrykandpatrick.vico.core.formatter.DecimalFormatValueFormatter
import com.patrykandpatrick.vico.core.formatter.ValueFormatter
import com.patrykandpatrick.vico.core.model.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.model.drawing.DefaultDrawingModelInterpolator
import com.patrykandpatrick.vico.core.model.drawing.DrawingModelInterpolator
import com.patrykandpatrick.vico.core.model.drawing.LineCartesianLayerDrawingModel

/**
 * Creates a [LineCartesianLayer].
 *
 * @param lines the [LineCartesianLayer.LineSpec]s to use for the lines. This list is iterated through as many times as
 * there are lines.
 * @param spacing the distance between neighboring major entries’ points.
 * @param axisValueOverrider overrides the _x_ and _y_ ranges.
 * @param verticalAxisPosition the position of the [VerticalAxis] with which the [LineCartesianLayer] should be
 * associated. Use this for independent [CartesianLayer] scaling.
 * @param drawingModelInterpolator interpolates the [LineCartesianLayer]’s [LineCartesianLayerDrawingModel]s.
 */
@Composable
public fun rememberLineCartesianLayer(
    lines: List<LineSpec> = currentChartStyle.lineLayer.lines,
    spacing: Dp = currentChartStyle.lineLayer.spacing,
    axisValueOverrider: AxisValueOverrider<LineCartesianLayerModel>? = null,
    verticalAxisPosition: AxisPosition.Vertical? = null,
    drawingModelInterpolator:
        DrawingModelInterpolator<LineCartesianLayerDrawingModel.PointInfo, LineCartesianLayerDrawingModel> =
        remember { DefaultDrawingModelInterpolator() },
): LineCartesianLayer =
    remember { LineCartesianLayer(lines) }.apply {
        this.lines = lines
        this.spacingDp = spacing.value
        this.axisValueOverrider = axisValueOverrider
        this.verticalAxisPosition = verticalAxisPosition
        this.drawingModelInterpolator = drawingModelInterpolator
    }

/**
 * Creates a [LineCartesianLayer.LineSpec] for use in [LineCartesianLayer]s.
 *
 * @param shader the [DynamicShader] for the line.
 * @param thickness the thickness of the line.
 * @param backgroundShader an optional [DynamicShader] to use for the areas bounded by the [LineCartesianLayer] line and
 * the zero line (_y_ = 0).
 * @param cap the stroke cap for the line.
 * @param point an optional [Component] that can be drawn at a given point on the line.
 * @param pointSize the size of the [point].
 * @param dataLabel an optional [TextComponent] to use for data labels.
 * @param dataLabelVerticalPosition the vertical position of data labels relative to the line.
 * @param dataLabelValueFormatter the [ValueFormatter] to use for data labels.
 * @param dataLabelRotationDegrees the rotation of data labels in degrees.
 * @param pointConnector the [LineSpec.PointConnector] for the line.
 */
public fun lineSpec(
    shader: DynamicShader,
    thickness: Dp = DefaultDimens.LINE_THICKNESS.dp,
    backgroundShader: DynamicShader? = shader.getDefaultBackgroundShader(),
    cap: StrokeCap = StrokeCap.Round,
    point: Component? = null,
    pointSize: Dp = DefaultDimens.POINT_SIZE.dp,
    dataLabel: TextComponent? = null,
    dataLabelVerticalPosition: VerticalPosition = VerticalPosition.Top,
    dataLabelValueFormatter: ValueFormatter = DecimalFormatValueFormatter(),
    dataLabelRotationDegrees: Float = 0f,
    pointConnector: LineSpec.PointConnector = DefaultPointConnector(),
): LineSpec =
    LineSpec(
        shader = shader,
        thicknessDp = thickness.value,
        backgroundShader = backgroundShader,
        cap = cap.paintCap,
        point = point,
        pointSizeDp = pointSize.value,
        dataLabel = dataLabel,
        dataLabelVerticalPosition = dataLabelVerticalPosition,
        dataLabelValueFormatter = dataLabelValueFormatter,
        dataLabelRotationDegrees = dataLabelRotationDegrees,
        pointConnector = pointConnector,
    )

private fun DynamicShader.getDefaultBackgroundShader(): DynamicShader? =
    when (this) {
        is ColorShader ->
            TopBottomShader(
                topShader =
                    DynamicShaders.fromBrush(
                        brush =
                            Brush.verticalGradient(
                                listOf(
                                    Color(color).copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    Color(color).copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                                ),
                            ),
                    ),
                bottomShader =
                    DynamicShaders.fromBrush(
                        brush =
                            Brush.verticalGradient(
                                listOf(
                                    Color(color).copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                                    Color(color).copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                ),
                            ),
                    ),
            )
        is TopBottomShader -> {
            val topShader = topShader
            val bottomShader = bottomShader
            if (topShader is ColorShader && bottomShader is ColorShader) {
                TopBottomShader(
                    topShader =
                        DynamicShaders.fromBrush(
                            brush =
                                Brush.verticalGradient(
                                    listOf(
                                        Color(topShader.color).copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                        Color(topShader.color).copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                                    ),
                                ),
                        ),
                    bottomShader =
                        DynamicShaders.fromBrush(
                            brush =
                                Brush.verticalGradient(
                                    listOf(
                                        Color(bottomShader.color).copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                                        Color(
                                            bottomShader.color,
                                        ).copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    ),
                                ),
                        ),
                )
            } else {
                null
            }
        }
        else -> null
    }

private val StrokeCap.paintCap: Paint.Cap
    get() =
        when (this) {
            StrokeCap.Butt -> Paint.Cap.BUTT
            StrokeCap.Round -> Paint.Cap.ROUND
            StrokeCap.Square -> Paint.Cap.SQUARE
            else -> throw IllegalArgumentException("Not `StrokeCap.Butt`, `StrokeCap.Round`, or `StrokeCap.Square`.")
        }
