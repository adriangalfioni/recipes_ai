package agalfioni.recipesai.ingredients_detector.domain.interfaces

interface ImageProcessingObserver {

    fun onCompressionCompleted(originalSizeBytes: Long, compressedSizeBytes: Long)
}
