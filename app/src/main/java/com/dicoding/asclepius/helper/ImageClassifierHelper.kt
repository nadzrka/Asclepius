@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.dicoding.asclepius.R
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.task.vision.classifier.ImageClassifier.ImageClassifierOptions.*
import java.util.Locale

class ImageClassifierHelper(
    private var threshold: Float = 0.1f,
    private var maxResults: Int = 3,
    private val modelName: String = "cancer_classification.tflite",
    private val context: Context,
    private val classifierListener: ClassifierListener?
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                builder()
                    .setScoreThreshold(threshold)
                    .setMaxResults(maxResults)
                    .build()
            )
        } catch (e: IllegalStateException) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        val bitmap = toBitmap(imageUri)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val image = TensorImage.fromBitmap(resizedBitmap)
        val results: List<Classifications>? = imageClassifier?.classify(image)

        if (results.isNullOrEmpty()) {
            classifierListener?.onError(context.getString(R.string.inference_failed))
            return
        }

        val highestCategory = results.first().categories.firstOrNull()
        if (highestCategory != null) {
            val resultsCategory = highestCategory.label
            val resultsScore = String.format(Locale.US, "%.2f", highestCategory.score * 100)
            classifierListener?.onResults(resultsCategory, "$resultsScore%")
        } else {
            classifierListener?.onError(context.getString(R.string.no_result_found))
        }
    }

    private fun toBitmap(imageUri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
        } else {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            bitmap.copy(Bitmap.Config.ARGB_8888, true)
        }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(resultsCategory: String, resultsScore: String)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
