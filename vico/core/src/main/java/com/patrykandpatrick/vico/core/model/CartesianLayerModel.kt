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

package com.patrykandpatrick.vico.core.model

import com.patrykandpatrick.vico.core.chart.layer.CartesianLayer
import com.patrykandpatrick.vico.core.extension.gcdWith
import com.patrykandpatrick.vico.core.model.drawing.DrawingModel
import kotlin.math.abs

/**
 * Stores a [CartesianLayer]’s data.
 */
public interface CartesianLayerModel {
    /**
     * Identifies this [CartesianLayerModel].
     */
    public val id: Int

    /**
     * The minimum _x_ value.
     */
    public val minX: Float

    /**
     * The maximum _x_ value.
     */
    public val maxX: Float

    /**
     * The minimum _y_ value.
     */
    public val minY: Float

    /**
     * The maximum _y_ value.
     */
    public val maxY: Float

    /**
     * Stores auxiliary data, including [DrawingModel]s.
     */
    public val extraStore: ExtraStore

    /**
     * Returns the greatest common divisor of the _x_ values’ differences.
     */
    public fun getXDeltaGcd(): Float

    /**
     * Creates a copy of this [CartesianLayerModel] with the given [ExtraStore].
     */
    public fun copy(extraStore: ExtraStore): CartesianLayerModel

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    /**
     * Represents a single entity in a [CartesianLayerModel].
     */
    public interface Entry {
        /**
         * The _x_ coordinate.
         */
        public val x: Float

        override fun equals(other: Any?): Boolean

        override fun hashCode(): Int
    }

    /**
     * Stores the minimum amount of data required to create a [CartesianLayerModel] and facilitates this creation.
     */
    public interface Partial {
        /**
         * Creates a full [CartesianLayerModel] with the given [ExtraStore] from this [Partial].
         */
        public fun complete(extraStore: ExtraStore = ExtraStore.empty): CartesianLayerModel
    }
}

internal fun List<CartesianLayerModel.Entry>.getXDeltaGcd(): Float {
    if (isEmpty()) return 1f
    val iterator = iterator()
    var prevX = iterator.next().x
    var gcd: Float? = null
    while (iterator.hasNext()) {
        val x = iterator.next().x
        val delta = abs(x - prevX)
        prevX = x
        if (delta != 0f) gcd = gcd?.gcdWith(delta) ?: delta
    }
    return gcd
        ?.also { require(it != 0f) { "The x values are too precise. The maximum precision is two decimal places." } }
        ?: 1f
}

internal inline fun <T : CartesianLayerModel.Entry> List<T>.forEachInIndexed(
    range: ClosedFloatingPointRange<Float>,
    padding: Int = 0,
    action: (Int, T, T?) -> Unit,
) {
    var start = 0
    var end = 0
    for (entry in this) {
        when {
            entry.x < range.start -> start++
            entry.x > range.endInclusive -> break
        }
        end++
    }
    start = (start - padding).coerceAtLeast(0)
    end = (end + padding).coerceAtMost(lastIndex)
    (start..end).forEach { action(it, this[it], getOrNull(it + 1)) }
}
