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

package com.patrykandpatrick.vico.views.chart.layer

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.layer.LineCartesianLayer
import com.patrykandpatrick.vico.views.R
import com.patrykandpatrick.vico.views.theme.getLineSpec
import com.patrykandpatrick.vico.views.theme.getNestedTypedArray
import com.patrykandpatrick.vico.views.theme.getRawDimension
import com.patrykandpatrick.vico.views.theme.use

/**
 * Creates a [LineCartesianLayer.LineSpec] using the provided [attrRes] and [styleResId].
 *
 * @param context the context used to retrieve the style information.
 * @param attrRes a theme attribute resource identifier used to retrieve the [LineCartesianLayer.LineSpec]’s style.
 * This can be [R.attr.line1Spec], [R.attr.line2Spec], or [R.attr.line3Spec].
 * @param styleResId if not 0, used to retrieve the style information from the provided style resource.
 * The provided style must define the [attrRes]. If [styleResId] is 0, the [attrRes] is retrieved from [Context]’s
 * theme.
 *
 * @see R.styleable.LineSpec
 */
public fun lineSpec(
    context: Context,
    @AttrRes attrRes: Int = R.attr.line1Spec,
    @StyleRes styleResId: Int = 0,
): LineCartesianLayer.LineSpec {
    val tempArray = IntArray(1)

    tempArray[0] = attrRes
    return context.obtainStyledAttributes(null, tempArray, 0, styleResId)
        .use { typedArray ->
            typedArray.getNestedTypedArray(
                context = context,
                resourceId = 0,
                styleableResourceId = R.styleable.LineSpec,
            ).getLineSpec(context)
        }
}

/**
 * Creates a [LineCartesianLayer] using the provided [List] of theme attribute resource identifiers and the given
 * [styleResId].
 *
 * @param context the context used to retrieve the style information.
 * @param styleResId if not 0, used to retrieve the style information from the provided style resource.
 * The [styleResId] should define the style of all of the following: [R.attr.line1Spec], [R.attr.line2Spec],
 * and [R.attr.line3Spec]. If [styleResId] is 0, the style attributes are retrieved from [Context]’s theme.
 *
 * @see lineSpec
 */
public fun lineCartesianLayer(
    context: Context,
    @StyleRes styleResId: Int = 0,
): LineCartesianLayer {
    val tempArray = IntArray(1)

    val lineSpecs =
        listOf(R.attr.line1Spec, R.attr.line2Spec, R.attr.line3Spec)
            .map { themeAttrResItem -> lineSpec(context, themeAttrResItem, styleResId) }

    tempArray[0] = R.styleable.LineCartesianLayerStyle_spacing
    val spacingDp =
        context.obtainStyledAttributes(null, tempArray)
            .use { typedArray ->
                typedArray.getRawDimension(
                    context = context,
                    index = R.styleable.LineCartesianLayerStyle_spacing,
                    defaultValue = DefaultDimens.POINT_SPACING,
                )
            }

    return LineCartesianLayer(
        lines = lineSpecs,
        spacingDp = spacingDp,
    )
}
