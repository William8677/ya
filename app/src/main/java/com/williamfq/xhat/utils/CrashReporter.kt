// src/main/java/com/williamfq/xhat/utils/CrashReporter.kt

package com.williamfq.xhat.utils

import android.content.Context
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class CrashReporter @Inject constructor() {
    private lateinit var crashlytics: FirebaseCrashlytics
    private var isInitialized = false

    companion object {
        private const val TAG = "CrashReporter"
        private const val USER_ID = "William8677"
        private const val TIMESTAMP = "2025-01-11 21:04:14"
    }

    fun initialize(context: Context) {
        try {
            crashlytics = FirebaseCrashlytics.getInstance()
            setupCrashlytics()
            isInitialized = true
            Timber.d("CrashReporter inicializado correctamente")
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error al inicializar CrashReporter")
        }
    }

    private fun setupCrashlytics() {
        crashlytics.apply {
            setCustomKey("user_id", USER_ID)
            setCustomKey("timestamp", TIMESTAMP)
            setCrashlyticsCollectionEnabled(true)
        }
    }

    fun reportException(exception: Exception) {
        if (!isInitialized) {
            Timber.tag(TAG).e("CrashReporter no inicializado")
            return
        }

        try {
            crashlytics.apply {
                setCustomKey("last_action", "custom_exception")
                setCustomKey("exception_time", System.currentTimeMillis())
                recordException(exception)
            }
            Timber.e(exception, "Excepción reportada: ${exception.message}")
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error al reportar excepción")
        }
    }

    fun logError(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (!isInitialized) {
            Timber.tag(TAG).e("CrashReporter no inicializado")
            return
        }

        try {
            crashlytics.apply {
                setCustomKey("error_priority", priority)
                setCustomKey("error_tag", tag ?: "NO_TAG")
                log("$priority/$tag: $message")
                throwable?.let { recordException(it) }
            }
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error al registrar error")
        }
    }

    fun logEvent(eventName: String, params: Map<String, String> = emptyMap()) {
        if (!isInitialized) {
            Timber.tag(TAG).e("CrashReporter no inicializado")
            return
        }

        try {
            crashlytics.apply {
                setCustomKey("event_name", eventName)
                params.forEach { (key, value) ->
                    setCustomKey(key, value)
                }
                log("Event: $eventName, Params: $params")
            }
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error al registrar evento")
        }
    }

    fun setUserIdentifier(userId: String) {
        if (!isInitialized) {
            Timber.tag(TAG).e("CrashReporter no inicializado")
            return
        }

        try {
            crashlytics.setUserId(userId)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error al establecer identificador de usuario")
        }
    }

    fun enableCrashReporting(enable: Boolean) {
        if (!isInitialized) {
            Timber.tag(TAG).e("CrashReporter no inicializado")
            return
        }

        try {
            crashlytics.setCrashlyticsCollectionEnabled(enable)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error al configurar reporte de crashes")
        }
    }
}
