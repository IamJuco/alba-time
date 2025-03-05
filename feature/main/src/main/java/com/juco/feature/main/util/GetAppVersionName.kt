package com.juco.feature.main.util



import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

fun getAppVersionName(context: Context): String {
    return try {
        val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        packageInfo.versionName ?: "Unknown"
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}
