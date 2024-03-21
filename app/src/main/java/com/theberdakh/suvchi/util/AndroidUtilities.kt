package com.theberdakh.suvchi.util

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


fun Activity.vibratePhone(milliSeconds: Long = 100) {
    val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(milliSeconds, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(100)
    }
}
fun downloadFile(activity: Activity, url: String, fileTitle: String) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && // 1.
        ContextCompat.checkSelfPermission( // 2.
            activity,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions( // 3.
            activity,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        showToast("Permission denied. Grant permissions and try again.")
        return
    }

    val request = DownloadManager.Request(Uri.parse(url)) // 5.
        .setNotificationVisibility( // 6.
            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        )
        .setDestinationInExternalPublicDir( // 7.
            Environment.DIRECTORY_DOWNLOADS, fileTitle
        )

    val downloadManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request) // 8.
    showToast("Download started")
}
fun highlightText(str: CharSequence, query: ArrayList<String>){
    var emptyCount = 0
    for (i in query){

    }
}