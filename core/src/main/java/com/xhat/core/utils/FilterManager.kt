
package com.xhat.core.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions


class FilterManager {

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()

    private val detector = FaceDetection.getClient(options)

    fun applyFilter(bitmap: Bitmap, filterBitmap: Bitmap): Bitmap {
        val canvas = Canvas(bitmap)
        val paint = Paint()

        // Detect faces in the image
        val image = InputImage.fromBitmap(bitmap, 0)
        detector.process(image)
            .addOnSuccessListener { faces ->
                for (face in faces) {
                    val boundingBox = face.boundingBox

                    // Example: Draw filterBitmap over the face
                    canvas.drawBitmap(
                        filterBitmap,
                        boundingBox.left.toFloat(),
                        boundingBox.top.toFloat(),
                        paint
                    )
                }
            }
            .addOnFailureListener { e ->
                Log.e("FilterManager", "Face detection failed: ${e.message}")
            }

        return bitmap
    }
}
