package com.theberdakh.suvchi.util

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.UnknownHostException

suspend fun checkHostResolution(context: Context, hostname: String): Boolean {
    val isResolved: Boolean = try {
        val address = withContext(Dispatchers.IO) {
            InetAddress.getByName(hostname)
        }
        true
    } catch (e: UnknownHostException) {
        Toast.makeText(context, "Xosting menen baylanısa almadı. Iltimas, internet baylanısıńızdı tekseriń.", Toast.LENGTH_LONG).show()
        false
    }
    return isResolved
}