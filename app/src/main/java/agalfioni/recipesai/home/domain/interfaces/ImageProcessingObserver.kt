package agalfioni.recipesai.home.domain.interfaces

interface ImageProcessingObserver {

    fun onCompressionCompleted(originalSizeBytes: Long, compressedSizeBytes: Long)
}
