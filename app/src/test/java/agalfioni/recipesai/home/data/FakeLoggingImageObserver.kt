package agalfioni.recipesai.home.data

import agalfioni.recipesai.home.domain.interfaces.ImageProcessingObserver

class FakeLoggingImageObserver: ImageProcessingObserver {

    override fun onCompressionCompleted(
        originalSizeBytes: Long,
        compressedSizeBytes: Long
    ) {
        // Do nothing
    }

}