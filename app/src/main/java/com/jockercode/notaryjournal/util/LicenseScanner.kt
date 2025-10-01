import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.jockercode.notaryjournal.model.DriverLicense

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
class LicenseScanner(
    private val onResult: (DriverLicense) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_PDF417)
        .build()

    private val scanner: BarcodeScanner = BarcodeScanning.getClient(options)

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        val rotation = imageProxy.imageInfo.rotationDegrees
        val inputImage = InputImage.fromMediaImage(mediaImage, rotation)

        scanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                Log.d("Scanner", "Barcodes detected: ${barcodes.size}")
                if (barcodes.isNotEmpty()) {
                    val raw = barcodes.first().rawValue
                    Log.d("Scanner", "Raw barcode: $raw")
                }
            }
            .addOnFailureListener {
                // handle errors safely
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}
