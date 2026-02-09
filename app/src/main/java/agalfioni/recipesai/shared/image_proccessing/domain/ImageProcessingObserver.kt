package agalfioni.recipesai.shared.image_proccessing.domain

interface ImageProcessingObserver {

    fun onCompressionCompleted(originalSizeBytes: Long, compressedSizeBytes: Long)
}