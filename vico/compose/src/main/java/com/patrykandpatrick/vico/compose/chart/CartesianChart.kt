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

package com.patrykandpatrick.vico.compose.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.core.axis.Axis
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.chart.CartesianChart
import com.patrykandpatrick.vico.core.chart.decoration.Decoration
import com.patrykandpatrick.vico.core.chart.edges.FadingEdges
import com.patrykandpatrick.vico.core.chart.layer.CartesianLayer
import com.patrykandpatrick.vico.core.legend.Legend
import com.patrykandpatrick.vico.core.marker.Marker

/**
 * Creates and remembers a [CartesianChart] with the given [CartesianLayer]s, axes, [Legend], and [FadingEdges]
 * instance. Adds the given [Decoration]s and persistent [Marker]s.
 *
 * @see rememberColumnCartesianLayer
 * @see rememberLineCartesianLayer
 */
@Composable
public fun rememberCartesianChart(
    vararg layers: CartesianLayer<*>,
    startAxis: Axis<AxisPosition.Vertical.Start>? = null,
    topAxis: Axis<AxisPosition.Horizontal.Top>? = null,
    endAxis: Axis<AxisPosition.Vertical.End>? = null,
    bottomAxis: Axis<AxisPosition.Horizontal.Bottom>? = null,
    legend: Legend? = null,
    fadingEdges: FadingEdges? = null,
    decorations: List<Decoration>? = null,
    persistentMarkers: Map<Float, Marker>? = null,
): CartesianChart =
    remember(*layers) { CartesianChart(*layers) }
        .apply {
            this.startAxis = startAxis
            this.topAxis = topAxis
            this.endAxis = endAxis
            this.bottomAxis = bottomAxis
            this.legend = legend
            this.fadingEdges = fadingEdges
            decorations?.let(::setDecorations)
            persistentMarkers?.let(::setPersistentMarkers)
        }
