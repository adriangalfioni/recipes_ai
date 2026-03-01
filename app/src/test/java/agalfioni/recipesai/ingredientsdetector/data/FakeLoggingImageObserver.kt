package agalfioni.recipesai.ingredientsdetector.data

import agalfioni.recipesai.ingredientsdetector.domain.interfaces.ImageProcessingObserver

class FakeLoggingImageObserver : ImageProcessingObserver {
    override fun onCompressionCompleted(
        originalSizeBytes: Long,
        compressedSizeBytes: Long,
    ) {
        // Do nothing
    }
}
