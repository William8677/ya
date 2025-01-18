package com.williamfq.xhat

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.StrictMode
import androidx.annotation.RequiresApi
import androidx.multidex.BuildConfig
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.InitializationStatus
import com.williamfq.xhat.utils.CrashReporter
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.Arrays
import javax.inject.Inject

@HiltAndroidApp
class XhatApplication : Application() {

    @Inject
    lateinit var crashReporter: CrashReporter // Inyección para el reporte de crashes

    companion object {
        private const val TAG = "XhatApplication"
        private const val TEST_DEVICE_ID = "ca-app-pub-2587938308176637~6448560139" // Tu ID de AdMob
        private const val MAX_MEMORY_THRESHOLD = 0.85 // 85% del máximo de memoria

        @Volatile
        private lateinit var instance: XhatApplication

        fun getInstance(): XhatApplication = instance
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        instance = this
        setupStrictMode()
        super.onCreate()

        initializeComponents()
    }

    private fun initializeComponents() {
        try {
            initializeTimber()
            initializeAdMob()
            initializeCrashReporting()
        } catch (e: Exception) {
            handleInitializationError(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .detectCustomSlowCalls()
                    .penaltyLog()
                    .penaltyDialog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .detectActivityLeaks()
                    .detectLeakedRegistrationObjects()
                    .detectFileUriExposure()
                    .detectContentUriWithoutPermission()
                    .penaltyLog()
                    .build()
            )

            Timber.d("StrictMode configurado para desarrollo")
        }
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree(crashReporter))
        }
    }

    private fun initializeAdMob() {
        try {
            configureAdMobForTesting()

            MobileAds.initialize(this, ::handleAdMobInitialization)
        } catch (e: Exception) {
            Timber.e(e, "Error en la inicialización de AdMob")
            crashReporter.reportException(e)
        }
    }

    private fun configureAdMobForTesting() {
        if (BuildConfig.DEBUG) {
            val configuration = RequestConfiguration.Builder()
                .setTestDeviceIds(listOf(TEST_DEVICE_ID))
                .build()
            MobileAds.setRequestConfiguration(configuration)
        }
    }

    private fun handleAdMobInitialization(initializationStatus: InitializationStatus) {
        initializationStatus.adapterStatusMap.forEach { (adapter, status) ->
            Timber.d("AdMob Adapter $adapter: ${status.description} (Latencia: ${status.latency}ms)")
        }
    }

    private fun initializeCrashReporting() {
        if (!BuildConfig.DEBUG) {
            crashReporter.initialize(this)
        }
    }

    private fun handleInitializationError(error: Exception) {
        Timber.e(error, "Error en la inicialización de la aplicación")
        crashReporter.reportException(error)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // Configuración adicional si es necesaria
    }

    override fun onLowMemory() {
        super.onLowMemory()
        handleLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        handleTrimMemory(level)
    }

    private fun handleLowMemory() {
        Timber.w("Memoria baja detectada")
        clearMemoryCache()
        System.gc()
    }

    private fun handleTrimMemory(level: Int) {
        when (level) {
            TRIM_MEMORY_RUNNING_MODERATE -> {
                Timber.d("Memoria moderada - Limpiando caches no esenciales")
                clearNonEssentialCaches()
            }
            TRIM_MEMORY_RUNNING_LOW -> {
                Timber.w("Memoria baja - Limpiando todos los caches")
                clearAllCaches()
            }
            TRIM_MEMORY_RUNNING_CRITICAL -> {
                Timber.e("Memoria crítica - Limpieza de emergencia")
                performEmergencyCleanup()
            }
        }
    }

    private fun clearMemoryCache() {
        // Implementar limpieza de caché de imágenes, etc.
    }

    private fun clearNonEssentialCaches() {
        // Implementar limpieza de cachés no esenciales
    }

    private fun clearAllCaches() {
        // Implementar limpieza de todos los cachés
    }

    private fun performEmergencyCleanup() {
        // Implementar limpieza de emergencia
        clearMemoryCache()
        clearAllCaches()
        System.gc()
    }

    private inner class DebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return String.format(
                "[%s:%s]",
                super.createStackElementTag(element),
                element.lineNumber
            )
        }

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            val timeStamp = java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                java.util.Locale.getDefault()
            ).format(java.util.Date())

            val enhancedMessage = "[$timeStamp][User: William8677] $message"
            super.log(priority, tag, enhancedMessage, t)
        }
    }

    private inner class ReleaseTree(private val crashReporter: CrashReporter) : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority >= android.util.Log.ERROR) {
                crashReporter.logError(priority, tag, message, t)
            }
        }
    }
}
