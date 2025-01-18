package com.williamfq.xhat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

/**
 * Ejemplo de Activity principal con Hilt y SplashScreen.
 * No solicita los permisos en el arranque.
 * Los solicita cuando el usuario pulsa un botón para usar la funcionalidad respectiva.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var errorHandler: com.williamfq.xhat.utils.ErrorHandler
    @Inject
    lateinit var logUtils: com.williamfq.xhat.utils.LogUtils

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>> // Maneja respuesta de permisos

    companion object {
        private const val SPLASH_DURATION_MS = 3000L
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Activa la Splash Screen
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Configuramos el lanzador de permisos (sin solicitar nada todavía)
        setupPermissionLauncher()

        // Mostramos la UI
        setContent {
            val showSplash = remember { mutableStateOf(true) }

            // Controlamos la duración de la Splash
            LaunchedEffect(Unit) {
                delay(SPLASH_DURATION_MS)
                showSplash.value = false
            }

            if (showSplash.value) {
                SplashScreen()
            } else {
                // Pantalla principal
                MainScreen()
            }
        }
    }

    /**
     * Configura el lanzador para manejar la respuesta de la solicitud de permisos.
     */
    private fun setupPermissionLauncher() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionResults ->
            // Maneja la respuesta de cada permiso solicitado
            permissionResults.forEach { (permission, isGranted) ->
                if (isGranted) {
                    showToast("Permiso concedido: $permission")
                    Timber.i("Permiso concedido: $permission")
                    // Aquí podrías iniciar directamente la funcionalidad
                } else {
                    showToast("Permiso denegado: $permission")
                    Timber.w("Permiso denegado: $permission")
                }
            }
        }
    }

    /**
     * Solicita un permiso específico solo cuando el usuario necesita la acción.
     */
    private fun requestSinglePermission(permission: String) {
        // Verificamos si ya está concedido
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            showToast("Permiso ya concedido: $permission")
            // Aquí puedes ejecutar la acción que requiere el permiso
        } else {
            // No lo tenemos => lo solicitamos
            permissionLauncher.launch(arrayOf(permission))
        }
    }

    /**
     * Funciones para acciones de ejemplo
     */
    private fun useCameraFeature() {
        // Verificamos si la app tiene el permiso de cámara
        requestSinglePermission(Manifest.permission.CAMERA)
        // Si se concede, la lógica para abrir la cámara irá en onActivityResult / callback o
        // se ejecuta automáticamente después de verificar isGranted, etc.
    }

    private fun useMicrophoneFeature() {
        requestSinglePermission(Manifest.permission.RECORD_AUDIO)
    }

    private fun useLocationFeature() {
        requestSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun useNotificationsFeature() {
        requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)
    }

    /**
     * Pantalla principal (después de la splash),
     * con botones para cada acción que requiera un permiso.
     */
    @Composable
    fun MainScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pantalla Principal",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Ejemplo de botón para usar la cámara
            Button(
                onClick = { useCameraFeature() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Usar Cámara")
            }

            // Ejemplo de botón para usar el micrófono
            Button(
                onClick = { useMicrophoneFeature() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Usar Micrófono")
            }

            // Ejemplo de botón para usar ubicación
            Button(
                onClick = { useLocationFeature() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Usar Ubicación")
            }

            // Ejemplo de botón para usar notificaciones (solo Android 13+ lo requiere)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Button(
                    onClick = { useNotificationsFeature() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text("Habilitar Notificaciones")
                }
            }
        }
    }

    /**
     * Composable de Splash Screen
     */
    @Composable
    fun SplashScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Bienvenido a Xhat",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    /**
     * Muestra un Toast rápido.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
