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

package com.patrykandpatrick.vico.core.component.shape.shader

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.RectF
import android.graphics.Shader
import com.patrykandpatrick.vico.core.context.DrawContext
import com.patrykandpatrick.vico.core.util.Point

/**
 * Applies the given color ([color]) to the shaded area.
 */
public class ColorShader(public val color: Int) : DynamicShader {
    private val shader =
        BitmapShader(
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888).apply { setPixel(0, 0, color) },
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP,
        )

    override fun provideShader(
        context: DrawContext,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
    ): Shader = shader

    override fun getColorAt(
        point: Point,
        drawContext: DrawContext,
        rectF: RectF,
    ): Int = color
}
