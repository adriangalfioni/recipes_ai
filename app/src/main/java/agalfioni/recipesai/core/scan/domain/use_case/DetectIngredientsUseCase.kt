package agalfioni.recipesai.core.scan.domain.use_case

import agalfioni.recipesai.core.ai.domain.detector.AiIngredientsDetector
import agalfioni.recipesai.core.domain.interfaces.LanguageProvider
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.map
import agalfioni.recipesai.core.scan.domain.model.IngredientsResult
import agalfioni.recipesai.shared.image_proccessing.domain.ImageProcessor
import agalfioni.recipesai.core.ingredients.domain.interfaces.IngredientsParser
import agalfioni.recipesai.core.scan.domain.providers.PromptProvider

class DetectIngredientsUseCase(
    private val aiIngredientsDetector: AiIngredientsDetector,
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

        val rawJsonResult = aiIngredientsDetector.detectIngredients(bytearray, prompt)

        return rawJsonResult.map(ingredientsParser::parseIngredients)
    }
}