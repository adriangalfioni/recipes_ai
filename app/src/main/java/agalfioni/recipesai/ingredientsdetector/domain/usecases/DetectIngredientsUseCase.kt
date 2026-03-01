package agalfioni.recipesai.ingredientsdetector.domain.usecases

import agalfioni.recipesai.core.domain.interfaces.LanguageProvider
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.map
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.ImageProcessor
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsDetector
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsParser
import agalfioni.recipesai.ingredientsdetector.domain.models.IngredientsResult
import agalfioni.recipesai.ingredientsdetector.domain.providers.PromptProvider

class DetectIngredientsUseCase(
    private val ingredientsDetector: IngredientsDetector,
    private val imageProcessor: ImageProcessor,
    private val languageProvider: LanguageProvider,
    private val ingredientsParser: IngredientsParser,
) {
    suspend operator fun invoke(uriString: String): AppResult<IngredientsResult, DataError> {
        val bytearray =
            try {
                imageProcessor.compressImageForAi(uriString)
            } catch (e: IllegalArgumentException) {
                return AppResult.Error(DataError.INVALID_ARGUMENT)
            }

        val prompt =
            PromptProvider.generateFridgeAnalyzerPrompt(
                languageProvider.getLanguage(),
            )

        val rawJsonResult = ingredientsDetector.detectIngredients(bytearray, prompt)

        return rawJsonResult.map(ingredientsParser::parseIngredients)
    }
}
