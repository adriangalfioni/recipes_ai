package agalfioni.recipesai.shared.image_proccessing.domain

interface ImageProcessor {

    suspend fun compressImageForAi(uriString: String, maxSize: Int = 1024, quality: Int = 70): ByteArray

}