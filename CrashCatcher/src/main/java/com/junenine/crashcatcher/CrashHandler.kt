package com.junenine.crashcatcher

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Intent
import android.os.Bundle
import com.junenine.crashcatcher.CrashCallback.onRestartActivity
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CrashHandler private constructor(
    private val application: Application
) {
    private var currentActivity: Activity? = null
    private var restartActivity: Activity? = null

    init {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            saveCrashLogToFile(throwable)
            launchCrashDialogActivity()
        }

        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(act: Activity, p1: Bundle?) {
                if (act is CrashDialogActivity) {
                    return
                }
                setActivity(act)
            }

            override fun onActivityStarted(act: Activity) {
            }

            override fun onActivityResumed(act: Activity) {
            }

            override fun onActivityPaused(act: Activity) {
            }

            override fun onActivityStopped(act: Activity) {
            }

            override fun onActivitySaveInstanceState(act: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(act: Activity) {
            }
        })

        onRestartActivity = {
            if (restartActivity != null) {
                launchRestartActivity()
            } else {
                currentActivity?.let {
                    restartActivity(it)
                }
            }
        }
    }

    fun setActivity(activity: Activity?) {
        currentActivity = activity
    }

    fun setRestartActivity(activity: Activity?) {
        restartActivity = activity
    }

    private fun launchRestartActivity() {
        restartActivity?.let {
            val intent = Intent(application.applicationContext, it::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            application.applicationContext.startActivity(intent)
        }
    }

    private fun launchCrashDialogActivity() {
        val intent = Intent(application.applicationContext, CrashDialogActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        application.applicationContext.startActivity(intent)
    }

    private fun saveCrashLogToFile(throwable: Throwable) {
        try {
            val crashLogDir = File(application.applicationContext.filesDir, "crash_logs")
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
            application: Application
        ): CrashHandler {
            return CrashHandler(application)
        }

        fun restartActivity(activity: Activity) {
            val intent = Intent(activity.applicationContext, activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.finish()
            activity.applicationContext.startActivity(intent)
        }
    }
}
