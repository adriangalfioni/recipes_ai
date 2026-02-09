package agalfioni.recipesai.home.data

import agalfioni.recipesai.shared.image_proccessing.domain.ImageProcessingObserver

class FakeLoggingImageObserver: ImageProcessingObserver {

    override fun onCompressionCompleted(
        originalSizeBytes: Long,
        compressedSizeBytes: Long
    ) {
        // Do nothing
    }

}