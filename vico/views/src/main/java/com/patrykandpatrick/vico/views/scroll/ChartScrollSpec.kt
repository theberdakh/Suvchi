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

package com.patrykandpatrick.vico.views.scroll

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import com.patrykandpatrick.vico.core.Animation
import com.patrykandpatrick.vico.core.model.CartesianChartModel
import com.patrykandpatrick.vico.core.scroll.AutoScrollCondition
import com.patrykandpatrick.vico.core.scroll.InitialScroll
import com.patrykandpatrick.vico.core.scroll.ScrollHandler

/**
 * Houses scrolling-related settings for charts.
 *
 * @property isScrollEnabled whether horizontal scrolling is enabled.
 * @property initialScroll represents the chart’s initial scroll position.
 * @property autoScrollCondition defines when an automatic scroll should be performed.
 * @property autoScrollInterpolator the [TimeInterpolator] to use for automatic scrolling.
 * @property autoScrollDuration the animation duration for automatic scrolling.
 */
public class ChartScrollSpec(
    public val isScrollEnabled: Boolean = true,
    public val initialScroll: InitialScroll = InitialScroll.Start,
    public val autoScrollCondition: AutoScrollCondition = AutoScrollCondition.Never,
    public val autoScrollInterpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    public val autoScrollDuration: Long = Animation.DIFF_DURATION.toLong(),
) {
    private val animator: ValueAnimator =
        ValueAnimator.ofFloat(
            Animation.range.start,
            Animation.range.endInclusive,
        ).apply {
            duration = autoScrollDuration
            interpolator = autoScrollInterpolator
        }

    /**
     * Performs an automatic scroll.
     */
    public fun performAutoScroll(
        model: CartesianChartModel,
        oldModel: CartesianChartModel?,
        scrollHandler: ScrollHandler,
    ) {
        if (!autoScrollCondition.shouldPerformAutoScroll(model, oldModel)) return
        with(receiver = animator) {
            cancel()
            removeAllUpdateListeners()
            addUpdateListener {
                scrollHandler.handleScroll(
                    targetScroll =
                        when (initialScroll) {
                            InitialScroll.Start -> (1 - it.animatedFraction) * scrollHandler.value
                            InitialScroll.End ->
                                scrollHandler.value + it.animatedFraction *
                                    (scrollHandler.maxValue - scrollHandler.value)
                        },
                )
            }
            start()
        }
    }
}

/**
 * Copies this [ChartScrollSpec], changing select values.
 */
public fun ChartScrollSpec.copy(
    isScrollEnabled: Boolean = this.isScrollEnabled,
    initialScroll: InitialScroll = this.initialScroll,
    autoScrollCondition: AutoScrollCondition = this.autoScrollCondition,
    autoScrollInterpolator: TimeInterpolator = this.autoScrollInterpolator,
    autoScrollDuration: Long = this.autoScrollDuration,
): ChartScrollSpec =
    ChartScrollSpec(
        isScrollEnabled = isScrollEnabled,
        initialScroll = initialScroll,
        autoScrollCondition = autoScrollCondition,
        autoScrollInterpolator = autoScrollInterpolator,
        autoScrollDuration = autoScrollDuration,
    )
