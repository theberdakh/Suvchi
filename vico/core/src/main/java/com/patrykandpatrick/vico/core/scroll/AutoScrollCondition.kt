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

package com.patrykandpatrick.vico.core.scroll

import com.patrykandpatrick.vico.core.model.CartesianChartModel

/**
 * Defines when an automatic scroll should be performed.
 */
public fun interface AutoScrollCondition {
    /**
     * Given a chart’s new and old models, defines whether an automatic scroll should be performed.
     */
    public fun shouldPerformAutoScroll(
        newModel: CartesianChartModel,
        oldModel: CartesianChartModel?,
    ): Boolean

    public companion object {
        /**
         * Prevents any automatic scrolling from occurring.
         */
        public val Never: AutoScrollCondition = AutoScrollCondition { _, _ -> false }

        /**
         * Triggers an automatic scroll when the size of the model increases (that is, the contents of the chart become
         * wider).
         */
        public val OnModelSizeIncreased: AutoScrollCondition =
            AutoScrollCondition { newModel, oldModel ->
                oldModel != null && (newModel.models.size > oldModel.models.size || newModel.width > oldModel.width)
            }
    }
}
