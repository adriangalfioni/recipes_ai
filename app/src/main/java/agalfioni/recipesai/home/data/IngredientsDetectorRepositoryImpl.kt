package agalfioni.recipesai.home.data

import agalfioni.recipesai.core.data.helpers.safeAiCall
import agalfioni.recipesai.core.data.repository.LocalIngredientsLoader
import agalfioni.recipesai.core.domain.interfaces.LanguageProvider
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.LocalIngredient
import agalfioni.recipesai.core.domain.models.map
import agalfioni.recipesai.home.data.utils.ImageProcessor
import agalfioni.recipesai.home.data.utils.PromptProvider
import agalfioni.recipesai.home.domain.interfaces.IngredientsDetectorRepository
import agalfioni.recipesai.home.domain.IngredientsResult
import android.net.Uri

class IngredientsDetectorRepositoryImpl(
    private val aiRemoteDataSource: IngredientsDetectorDataSource,
    private val imageProcessor: ImageProcessor,
    private val localIngredientsLoader: LocalIngredientsLoader,
    private val languageProvider: LanguageProvider
) : IngredientsDetectorRepository {
    override suspend fun analyzeFridge(uri: Uri): AppResult<IngredientsResult, DataError> {
        val bytearray = try {
            imageProcessor.compressImageForAi(uri)
        } catch (e: IllegalArgumentException) {
            return AppResult.Error(DataError.INVALID_ARGUMENT)
        }

        val rawJsonResult = safeAiCall {
            // Call AI with specific prompt (Business detail)
            aiRemoteDataSource.generateContent(
                bytearray,
                PromptProvider.generateFridgeAnalyzerPrompt(
                    languageProvider.getLanguage()
                )
            )
        }

        return rawJsonResult.map { rawJson -> parseIngredients(rawJson)}
    }

    override suspend fun getLocalIngredients(): Result<List<LocalIngredient>> {
        return Result.success(localIngredientsLoader.loadIngredients())
    }
}