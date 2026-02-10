package agalfioni.recipesai.ingredients_detector.data

import agalfioni.recipesai.ingredients_detector.domain.interfaces.ImageProcessingObserver

class FakeLoggingImageObserver: ImageProcessingObserver {

    override fun onCompressionCompleted(
        originalSizeBytes: Long,
        compressedSizeBytes: Long
    ) {
        // Do nothing
    }

}