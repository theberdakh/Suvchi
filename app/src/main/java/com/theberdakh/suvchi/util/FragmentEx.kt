package com.theberdakh.suvchi.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun replaceFragment(fragmentManager: FragmentManager, @IdRes fragmentContainer: Int, fragment: Fragment){
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(fragmentContainer, fragment)
    transaction.commit()
}