package agalfioni.recipesai.ingredientsdetector.domain.interfaces

interface ImageProcessingObserver {
    fun onCompressionCompleted(
        originalSizeBytes: Long,
        compressedSizeBytes: Long,
    )
}
