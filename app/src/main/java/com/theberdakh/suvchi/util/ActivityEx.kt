package com.theberdakh.suvchi.util

import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar

fun setOnlyLightMode() {
    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}

fun View.showSnackbar(text: String, duration: Int = Snackbar.LENGTH_LONG){
    val snackbar = Snackbar.make(this, text, duration)
    snackbar.show()
}
