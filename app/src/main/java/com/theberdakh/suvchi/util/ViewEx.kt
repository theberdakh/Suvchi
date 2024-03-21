package com.theberdakh.suvchi.util

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.app.App

/* View Ex*/
fun View.hide(){
    this.visibility = View.GONE
}
fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

/* TextView Ex */
fun TextView.setDrawableLeft(@DrawableRes drawable: Int){
    this.setCompoundDrawablesWithIntrinsicBounds(drawable,0,0, 0)
}
fun TextView.setDrawableTop(@DrawableRes drawable: Int){
    this.setCompoundDrawablesWithIntrinsicBounds(0,drawable,0, 0)
}
fun TextView.setDrawableRight(@DrawableRes drawable: Int){
    this.setCompoundDrawablesWithIntrinsicBounds(0,0,drawable, 0)
}
fun TextView.setDrawableBottom(@DrawableRes drawable: Int){
    this.setCompoundDrawablesWithIntrinsicBounds(0,0,0, drawable)
}
/*Pre-load views*/
fun showToast(text: String, duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(App.instance, text, duration).show()
}

fun View.showSnackbar(text: String, duration: Int = Snackbar.LENGTH_LONG){
    Snackbar.make(this, text, duration ).show()
}

/*Edit Text*/
fun EditText.isNotEmptyOrBlank() = this.getString().isNotEmptyOrBlank()
fun EditText.getStringIsEmptyOrBlank() = this.getString().isEmptyOrBlank()
fun EditText.getString() = this.text.toString()
fun EditText.shakeIfEmptyOrBlank(): Boolean {
    val isValid = if (this.getString().isEmptyOrBlank()) {
        this.shake()
        false
    } else {
        true
    }
    return isValid
}

fun EditText.shakeIf(validator: (String) -> Boolean): Boolean {
    val text = this.getString()
    val isValid: Boolean = if (validator.invoke(text)) {
        this.shake()
        false
    } else {
        true
    }

    return isValid
}

fun String.isEmptyOrBlank() = this.isEmpty() || this.isBlank()
fun String.isNotEmptyOrBlank() = this.isNotEmpty() && this.isNotBlank()

fun View.shake() {
    /*investigate all view animations */
    val shakeAnimation = AnimationUtils.loadAnimation(App.instance, R.anim.shake_animation)
    this.startAnimation(shakeAnimation)
}


/* AppCompatDelegate */
fun setOnlyLightMode() {
    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}





