package com.williamfq.xhat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {

    private var permissionsGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar si los permisos ya han sido otorgados
        permissionsGranted = checkPermissions()

        // Configurar contenido de Compose
        setContent {
            MaterialTheme {
                MainScreen(
                    permissionsGranted = permissionsGranted,
                    onRequestPermissions = { requestPermissions() }
                )
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permiso otorgado
                permissionsGranted = true
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show()
                recreate() // Recargar la actividad para actualizar la interfaz
            } else {
                // Permiso denegado
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
            }
        }

        // Solicitar el permiso necesario
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
    }
}
