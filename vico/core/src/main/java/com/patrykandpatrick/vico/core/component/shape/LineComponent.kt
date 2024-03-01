/*
 * Copyright 2023 by Patryk Goworowski and Patrick Michalik.
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

package com.patrykandpatrick.vico.core.component.shape

import android.graphics.Color
import android.graphics.RectF
import com.patrykandpatrick.vico.core.component.shape.Shapes.rectShape
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShader
import com.patrykandpatrick.vico.core.context.DrawContext
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.dimensions.Dimensions
import com.patrykandpatrick.vico.core.dimensions.emptyDimensions

/**
 * Draws a line.
 * @property color the background color.
 * @property thicknessDp the thickness of the line.
 * @property shape the [Shape] to use for the line.
 * @property dynamicShader an optional [DynamicShader] to apply to the line.
 * @property margins the margins of the line.
 * @property strokeWidthDp the stroke width.
 * @property strokeColor the stroke color.
 */
public open class LineComponent(
    color: Int,
    public var thicknessDp: Float = 2f,
    shape: Shape = rectShape,
    dynamicShader: DynamicShader? = null,
    margins: Dimensions = emptyDimensions(),
    strokeWidthDp: Float = 0f,
    strokeColor: Int = Color.TRANSPARENT,
) : ShapeComponent(shape, color, dynamicShader, margins, strokeWidthDp, strokeColor) {
    private val MeasureContext.thickness: Float
        get() = thicknessDp.pixels

    /**
     * A convenience function for [draw] that draws the [LineComponent] horizontally.
     */
    public open fun drawHorizontal(
        context: DrawContext,
        left: Float,
        right: Float,
        centerY: Float,
        thicknessScale: Float = 1f,
        opacity: Float = 1f,
    ): Unit =
        with(context) {
            draw(
                context,
                left = left,
                top = centerY - thickness * thicknessScale / 2,
                right = right,
                bottom = centerY + thickness * thicknessScale / 2,
                opacity = opacity,
            )
        }

    /**
     * Checks whether the [LineComponent] fits horizontally within the given [boundingBox] with its current
     * [thicknessDp].
     */
    public open fun fitsInHorizontal(
        context: DrawContext,
        left: Float,
        right: Float,
        centerY: Float,
        boundingBox: RectF,
        thicknessScale: Float = 1f,
    ): Boolean =
        with(context) {
            boundingBox.contains(
                left,
                centerY - thickness * thicknessScale / 2,
                right,
                centerY + thickness * thicknessScale / 2,
            )
        }

    /**
     * A convenience function for [draw] that draws the [LineComponent] vertically.
     */
    public open fun drawVertical(
        context: DrawContext,
        top: Float,
        bottom: Float,
        centerX: Float,
        thicknessScale: Float = 1f,
        opacity: Float = 1f,
    ): Unit =
        with(context) {
            draw(
                context,
                left = centerX - thickness * thicknessScale / 2,
                top = top,
                right = centerX + thickness * thicknessScale / 2,
                bottom = bottom,
                opacity = opacity,
            )
        }

    /**
     * Checks whether the [LineComponent] fits vertically within the given [boundingBox] with its current [thicknessDp].
     */
    public open fun fitsInVertical(
        context: DrawContext,
        top: Float,
        bottom: Float,
        centerX: Float,
        boundingBox: RectF,
        thicknessScale: Float = 1f,
    ): Boolean =
        with(context) {
            boundingBox.contains(
                centerX - thickness * thicknessScale / 2,
                top,
                centerX + thickness * thicknessScale / 2,
                bottom,
            )
        }

    /**
     * Checks whether the [LineComponent] vertically intersects the given [boundingBox] with its current [thicknessDp].
     */
    public open fun intersectsVertical(
        context: DrawContext,
        top: Float,
        bottom: Float,
        centerX: Float,
        boundingBox: RectF,
        thicknessScale: Float = 1f,
    ): Boolean =
        with(context) {
            boundingBox.intersects(
                centerX - thickness * thicknessScale / 2,
                top,
                centerX + thickness * thicknessScale / 2,
                bottom,
            )
        }
}
