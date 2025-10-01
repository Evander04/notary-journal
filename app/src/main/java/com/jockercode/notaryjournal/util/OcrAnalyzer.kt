import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
class OcrAnalyzer(
    private val onResult: (String) -> Unit,
    private val stopCamera: () -> Unit
) : ImageAnalysis.Analyzer {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private var alreadyScanned = false

    override fun analyze(imageProxy: ImageProxy) {
        if (alreadyScanned) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                val rawText = visionText.text
                if (rawText.contains("DOB") && !alreadyScanned) {
                    alreadyScanned = true
                    onResult(rawText)
                    stopCamera()
                }
            }
            .addOnFailureListener { e ->
                Log.e("OCR", "Failed: ", e)
            }
            .addOnCompleteListener { imageProxy.close() }
    }
}


