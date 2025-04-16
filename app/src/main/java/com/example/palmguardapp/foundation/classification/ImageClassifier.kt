import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.palmguardapp.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


class ImageClassifier(private val context: Context) {

    private val imageSize = 224
    private val classes = arrayOf("Brown Spots", "Healthy", "Unknown")

    private val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(imageSize, imageSize, ResizeOp.ResizeMethod.BILINEAR))
        .add(NormalizeOp(0f, 255f))
        .build()

    fun classifyImage(image: Bitmap): Pair<String, Float>? {
        val model = Model.newInstance(context)

        val tensorImage = TensorImage(DataType.FLOAT32)
        val convertedBitmap = image.copy(Bitmap.Config.ARGB_8888, true)
        tensorImage.load(convertedBitmap)
        val processedImage = imageProcessor.process(tensorImage)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, imageSize, imageSize, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(processedImage.buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        Log.d("ImageClassifier", "Confidence: ${confidences.size}")
        for (i in confidences.indices) {
            Log.d("ImageClassifier", "Class $i (${classes[i]}): ${confidences[i]}")
        }

        val maxPos = confidences.indices.maxByOrNull { confidences[it] } ?: -1
        val maxConfidence =  (maxOf(confidences[maxPos]) * 100).toFloat()
        Log.d("ImageClassifier", "Max Position: $maxPos, Max Confidence: $maxConfidence")

        model.close()

        return if (maxPos >= 0 && maxConfidence > THRESHOLD_CONFIDENCE && classes[maxPos] != "Unknown") {
            Pair(classes[maxPos], maxConfidence)
        } else {
            null
        }
    }

    companion object {
        private const val THRESHOLD_CONFIDENCE = 0.5f
    }
}