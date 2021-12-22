package net.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.text.TextUtils

object PackageUtils {
    fun checkAppInstalled(context: Context, pkgName: String): Boolean {
        if (TextUtils.isEmpty(pkgName)) {
            return false
        }

        var packageInfo: PackageInfo? = null
        packageInfo = try {
            context.packageManager.getPackageInfo(pkgName, 0)
        } catch (e: Exception) {
            null
        }
        return packageInfo != null
    }

    fun startApp(context: Context, uri: Uri) {
        val i = Intent(Intent.ACTION_VIEW, uri)
        i.setPackage("com.google.android.apps.maps")
        context.startActivity(i)
    }
}