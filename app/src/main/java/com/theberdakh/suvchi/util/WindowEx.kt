package com.theberdakh.suvchi.util

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi


fun Activity.enterFullScreen() {
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

@RequiresApi(Build.VERSION_CODES.O)
fun Activity.exitFullScreen() {
    this.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    this.window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST)
}
