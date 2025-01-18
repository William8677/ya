package com.williamfq.xhat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.williamfq.xhat.databinding.ActivityCallBinding

class CallActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCallBinding

    // Lanzador para solicitar cámara y micrófono y manejar la respuesta
    private val requestCameraAudioPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
            val audioGranted = permissions[Manifest.permission.RECORD_AUDIO] ?: false

            if (cameraGranted && audioGranted) {
                startCallLogic()
            } else {
                Toast.makeText(this, "Debes conceder cámara y micrófono para la llamada", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnStartCall = binding.btnStartCall
        val btnEndCall = binding.btnEndCall
        val btnMute = binding.btnMute
        val btnHangup = binding.btnHangup
        val btnSwitchCamera = binding.btnSwitchCamera

        // Iniciar Llamada
        btnStartCall.setOnClickListener {
            iniciarLlamada()
        }

        // Finalizar Llamada
        btnEndCall.setOnClickListener {
            finalizarLlamada()
        }

        // Silenciar Micrófono
        btnMute.setOnClickListener {
            silenciarMicrófono()
        }

        // Colgar Llamada
        btnHangup.setOnClickListener {
            colgarLlamada()
        }

        // Cambiar Cámara
        btnSwitchCamera.setOnClickListener {
            cambiarCamara()
        }
    }

    /**
     * Verifica si ya tenemos los permisos de cámara y micrófono.
     */
    private fun areCameraAndAudioGranted(): Boolean {
        val cameraGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val micGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        return cameraGranted && micGranted
    }

    /**
     * Inicia la lógica de la llamada, pidiendo permisos si hacen falta.
     */
    private fun iniciarLlamada() {
        if (areCameraAndAudioGranted()) {
            // Permisos ya concedidos
            startCallLogic()
        } else {
            // Solicitamos ambos permisos
            requestCameraAudioPermissionsLauncher.launch(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            )
        }
    }

    /**
     * Lógica real para iniciar la llamada.
     * Se llama solo cuando ya tenemos cámara y micrófono.
     */
    private fun startCallLogic() {
        Toast.makeText(this, "Llamada iniciada", Toast.LENGTH_SHORT).show()
        // Aquí implementa tu lógica de inicio de llamada
    }

    private fun finalizarLlamada() {
        Toast.makeText(this, "Llamada finalizada", Toast.LENGTH_SHORT).show()
        // Lógica real para finalizar la llamada
    }

    private fun silenciarMicrófono() {
        Toast.makeText(this, "Micrófono silenciado", Toast.LENGTH_SHORT).show()
        // Lógica real para silenciar el micrófono
    }

    private fun colgarLlamada() {
        Toast.makeText(this, "Llamada colgada", Toast.LENGTH_SHORT).show()
        // Lógica real para colgar la llamada
    }

    private fun cambiarCamara() {
        Toast.makeText(this, "Cámara cambiada", Toast.LENGTH_SHORT).show()
        // Lógica real para cambiar cámara (frontal/posterior)
    }
}
