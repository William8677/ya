package com.williamfq.xhat.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.slider.Slider
import com.williamfq.xhat.R
import com.williamfq.xhat.databinding.ViewStoryEditorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoryEditorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ViewStoryEditorBinding
    private var currentImageUri: Uri? = null
    private var currentBitmap: Bitmap? = null
    private lateinit var gestureDetector: GestureDetectorCompat
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null

    // Estado del editor
    private var currentFilter: String? = null
    private var currentStickers: MutableList<View> = mutableListOf()
    private var currentText: MutableList<View> = mutableListOf()
    private var isRecording = false

    init {
        binding = ViewStoryEditorBinding.inflate(LayoutInflater.from(context), this, true)
        setupUI()
        setupGestureDetector()
        setupBottomSheet()
    }

    private fun setupUI() {
        with(binding) {
            btnClose.setOnClickListener { cleanup() }
            btnSave.setOnClickListener { saveStory() }
            setupEditingTools()
            setupMediaControls()
        }
    }

    private fun setupEditingTools() {
        with(binding) {
            btnAddText.setOnClickListener { showTextEditor() }
            btnDraw.setOnClickListener { enableDrawingMode() }
            btnFilters.setOnClickListener { showFiltersPanel() }
            btnStickers.setOnClickListener { showStickersPanel() }
            btnMusic.setOnClickListener { showMusicSelector() }
        }
    }

    private fun setupMediaControls() {
        with(binding) {
            brightnessSlider.addOnChangeListener { _: Slider, value: Float, fromUser: Boolean ->
                if (fromUser) {
                    adjustBrightness(value)
                }
            }

            contrastSlider.addOnChangeListener { _: Slider, value: Float, fromUser: Boolean ->
                if (fromUser) {
                    adjustContrast(value)
                }
            }
        }
    }

    private fun setupGestureDetector() {
        gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                return true
            }
        })
    }

    private fun adjustBrightness(value: Float) {
        currentBitmap?.let { bitmap ->
            // Implementar el ajuste de brillo
        }
    }

    private fun adjustContrast(value: Float) {
        currentBitmap?.let { bitmap ->
            // Implementar el ajuste de contraste
        }
    }

    private suspend fun loadBitmapFromUri(uri: Uri): Bitmap {
        return withContext(Dispatchers.IO) {
            context.contentResolver.openInputStream(uri)?.use { input ->
                BitmapFactory.decodeStream(input)
            } ?: throw IllegalStateException("No se pudo cargar la imagen")
        }
    }

    fun setMediaUri(uri: Uri) {
        currentImageUri = uri
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val bitmap = loadBitmapFromUri(uri)
                withContext(Dispatchers.Main) {
                    binding.imagePreview.setImageBitmap(bitmap)
                    currentBitmap = bitmap
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Error al cargar la imagen")
                }
            }
        }
    }

    private fun showTextEditor() {
        binding.textEditorPanel.visibility = View.VISIBLE
    }

    private fun enableDrawingMode() {
        binding.drawingCanvas.visibility = View.VISIBLE
    }

    private fun showFiltersPanel() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun showStickersPanel() {
        binding.stickersPanel.visibility = View.VISIBLE
    }

    private fun showMusicSelector() {
        binding.musicPanel.visibility = View.VISIBLE
    }

    private fun saveStory() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val finalBitmap = createFinalImage()
                // Implementar guardado
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Historia guardada con éxito", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Error al guardar la historia")
                }
            }
        }
    }

    private fun createFinalImage(): Bitmap {
        return currentBitmap ?: throw IllegalStateException("No hay imagen para guardar")
    }

    private fun setupBottomSheet() {
        binding.toolsPanel.let {
            bottomSheetBehavior = BottomSheetBehavior.from(it)
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun cleanup() {
        currentBitmap?.recycle()
        currentBitmap = null
        currentImageUri = null
        removeAllViews()
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}
