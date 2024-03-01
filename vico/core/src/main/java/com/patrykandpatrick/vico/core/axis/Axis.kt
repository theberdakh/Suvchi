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

package com.patrykandpatrick.vico.core.axis

import android.graphics.RectF
import com.patrykandpatrick.vico.core.chart.CartesianChart
import com.patrykandpatrick.vico.core.chart.dimensions.MutableHorizontalDimensions
import com.patrykandpatrick.vico.core.chart.draw.ChartDrawContext
import com.patrykandpatrick.vico.core.chart.insets.ChartInsetter
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.dimensions.BoundsAware

/**
 * Defines the minimal set of properties and functions required by other parts of the library to draw an axis.
 */
public interface Axis<Position : AxisPosition> : BoundsAware, ChartInsetter {
    /**
     * Defines the position of the axis relative to the [CartesianChart].
     */
    public val position: Position

    /**
     * Called before the [CartesianChart] is drawn. Implementations should rely on this function to draw themselves,
     * unless they need to draw something above the [CartesianChart].
     *
     * @param context holds the information needed to draw the axis.
     *
     * @see drawAboveChart
     */
    public fun drawBehindChart(context: ChartDrawContext)

    /**
     * Called after the [CartesianChart] is drawn. Implementations can use this function to draw content above the
     * [CartesianChart].
     *
     * @param context holds the information needed to draw the axis.
     */
    public fun drawAboveChart(context: ChartDrawContext)

    /**
     * The bounds ([RectF]) passed here define the area where the [Axis] shouldn’t draw anything.
     */
    public fun setRestrictedBounds(vararg bounds: RectF?)

    /**
     * Updates the chart’s [MutableHorizontalDimensions] instance.
     */
    public fun updateHorizontalDimensions(
        context: MeasureContext,
        horizontalDimensions: MutableHorizontalDimensions,
    )
}
