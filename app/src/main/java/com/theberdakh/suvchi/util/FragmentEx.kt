package com.theberdakh.suvchi.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.theberdakh.suvchi.R

fun replaceFragment(
    fragmentManager: FragmentManager,
    @IdRes fragmentContainer: Int,
    fragment: Fragment
) {
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(fragmentContainer, fragment)
    transaction.commit()
}

fun addFragmentToBackStack(
    fragmentManager: FragmentManager,
    @IdRes fragmentContainer: Int,
    fragment: Fragment
) {
    val fragmentPopped = fragmentManager.popBackStackImmediate(fragment.tag, 0)
    if (!fragmentPopped && fragmentManager.findFragmentByTag(fragment.tag) == null) {
        val transaction = fragmentManager.beginTransaction()
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.add(fragmentContainer, fragment)
        transaction.commit()
    }
}