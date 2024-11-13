package com.junenine.crashcatcher

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.junenine.crashcatcher.CrashCallback.onRestartActivity
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CrashHandler private constructor(
    private val context: Context,
    private val mainActivity: Activity
) {

    init {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            saveCrashLogToFile(throwable)
            launchCrashDialogActivity()
        }
        onRestartActivity = {
            restartActivity(mainActivity)
        }
    }

    private fun launchCrashDialogActivity() {
        val intent = Intent(context, CrashDialogActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    private fun saveCrashLogToFile(throwable: Throwable) {
        try {
            val crashLogDir = File(context.filesDir, "crash_logs")
            if (!crashLogDir.exists()) {
                crashLogDir.mkdirs()
            }

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val crashLogFile = File(crashLogDir, "crash_log_$timestamp.txt")

            FileOutputStream(crashLogFile).use { fos ->
                PrintWriter(fos).use { pw ->
                    pw.println("Crash Time: $timestamp")
                    pw.println("Thread: ${Thread.currentThread().name}")
                    pw.println("Exception: ${throwable.localizedMessage}")
                    pw.println("Stack Trace:")
                    throwable.printStackTrace(pw)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // Log error if writing fails
        }
    }

    companion object {
        fun initialize(
            activity: Activity
        ) {
            CrashHandler(activity.applicationContext, activity)
        }

        fun restartActivity(activity: Activity) {
            val intent = Intent(activity.applicationContext, activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.finish()
            activity.applicationContext.startActivity(intent)
        }
    }
}
