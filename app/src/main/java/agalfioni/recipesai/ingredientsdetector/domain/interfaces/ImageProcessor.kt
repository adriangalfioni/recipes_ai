package agalfioni.recipesai.ingredientsdetector.domain.interfaces

interface ImageProcessor {
    suspend fun compressImageForAi(
        uriString: String,
        maxSize: Int = 1024,
        quality: Int = 70,
    ): ByteArray
}
