package agalfioni.recipesai.home.domain

import agalfioni.recipesai.core.domain.interfaces.LanguageProvider
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.map
import agalfioni.recipesai.home.domain.providers.PromptProvider
import agalfioni.recipesai.home.domain.interfaces.ImageProcessor
import agalfioni.recipesai.home.domain.interfaces.IngredientsParser
import agalfioni.recipesai.home.domain.interfaces.IngredientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetectIngredientsUseCase(
    private val repository: IngredientsRepository,
    private val imageProcessor: ImageProcessor,
    private val languageProvider: LanguageProvider,
    private val ingredientsParser: IngredientsParser
) {
    suspend operator fun invoke(
        uriString: String
    ): AppResult<IngredientsResult, DataError> {
        val bytearray = try {
            imageProcessor.compressImageForAi(uriString)
        } catch (e: IllegalArgumentException) {
            return AppResult.Error(DataError.INVALID_ARGUMENT)
        }

        val prompt = PromptProvider.generateFridgeAnalyzerPrompt(
            languageProvider.getLanguage()
        )

        val rawJsonResult = repository.detectIngredientsAI(bytearray, prompt)

        return rawJsonResult.map(ingredientsParser::parseIngredients)
    }
}