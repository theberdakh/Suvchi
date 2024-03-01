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

import com.patrykandpatrick.vico.core.chart.CartesianChart
import com.patrykandpatrick.vico.core.extension.gcdWith
import com.patrykandpatrick.vico.core.model.drawing.DrawingModel

/**
 * Stores a [CartesianChart]’s data.
 */
public class CartesianChartModel {
    /**
     * The [CartesianLayerModel]s.
     */
    public val models: List<CartesianLayerModel>

    /**
     * Identifies this [CartesianChartModel] in terms of the [CartesianLayerModel.id]s.
     */
    public val id: Int

    /**
     * Expresses the size of this [CartesianChartModel] in terms of the range of the _x_ values covered.
     */
    public val width: Float

    /**
     * Stores auxiliary data, including [DrawingModel]s.
     */
    public val extraStore: ExtraStore

    /**
     * Creates a [CartesianChartModel] consisting of the given [CartesianLayerModel]s.
     */
    public constructor(models: List<CartesianLayerModel>) : this(models, ExtraStore.empty)

    /**
     * Creates a [CartesianChartModel] consisting of the given [CartesianLayerModel]s.
     */
    public constructor(vararg models: CartesianLayerModel) : this(models.toList())

    internal constructor(models: List<CartesianLayerModel>, extraStore: ExtraStore) : this(
        models = models,
        id = models.map { it.id }.hashCode(),
        width = models.maxOf { it.maxX } - models.minOf { it.minX },
        extraStore = extraStore,
    )

    internal constructor(
        models: List<CartesianLayerModel>,
        id: Int,
        width: Float,
        extraStore: ExtraStore,
    ) {
        this.models = models
        this.id = id
        this.width = width
        this.extraStore = extraStore
    }

    /**
     * Returns the greatest common divisor of the _x_ values’ differences.
     */
    public fun getXDeltaGcd(): Float =
        models
            .fold<CartesianLayerModel, Float?>(null) { gcd, layerModel ->
                val layerModelGcd = layerModel.getXDeltaGcd()
                gcd?.gcdWith(layerModelGcd) ?: layerModelGcd
            }
            ?: 1f

    /**
     * Creates a copy of this [CartesianChartModel] with the given [ExtraStore], which is also applied to the
     * [CartesianLayerModel]s.
     */
    public fun copy(extraStore: ExtraStore): CartesianChartModel =
        CartesianChartModel(models.map { it.copy(extraStore) }, id, width, extraStore)

    /**
     * Creates an immutable copy of this [CartesianChartModel].
     */
    public fun toImmutable(): CartesianChartModel = this

    public companion object {
        /**
         * An empty [CartesianChartModel].
         */
        public val empty: CartesianChartModel =
            CartesianChartModel(models = emptyList(), id = 0, width = 0f, extraStore = ExtraStore.empty)
    }
}
