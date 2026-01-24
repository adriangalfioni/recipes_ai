package agalfioni.recipesai.core.data.repository

import agalfioni.recipesai.core.domain.models.LocalIngredient
import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class LocalIngredientsLoader(
    private val assetManager: AssetManager
) {

    private var cachedRawIngredients: List<LocalIngredient>? = null

    // Mutex ensures only one thread reads the file at a time
    private val mutex = Mutex()

    suspend fun loadIngredients(
        forcedLocale: Locale? = null
    ): List<LocalIngredient> = withContext(Dispatchers.IO) {
        // Get the raw data (either from cache or file)
        val rawList = getOrLoadRawData()

        // Always map to the current language (Cheap operation)
        val languageCode = (forcedLocale ?: Locale.getDefault()).language
        val isSpanish = languageCode == "es"

        return@withContext rawList.sortedBy {
            if (isSpanish) {
                it.es
            } else {
                it.en
            }
        }
    }

    // Logic to ensure we only read the file ONCE
    private suspend fun getOrLoadRawData(): List<LocalIngredient> {
        mutex.withLock {
            // If we already have it, return it immediately
            cachedRawIngredients?.let { return it }

            // Otherwise, do the heavy lifting
            val jsonString = getJsonFromAssets("meal_ingredients.json") ?: return emptyList()
            val gson = Gson()
            val listType = object : TypeToken<List<LocalIngredient>>() {}.type
            val loadedList: List<LocalIngredient> = gson.fromJson(jsonString, listType)

            // Save to cache and return
            cachedRawIngredients = loadedList
            return loadedList
        }
    }

    private fun getJsonFromAssets(fileName: String): String? {
        return try {
            assetManager.open(fileName).use { inputStream ->
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                String(buffer, Charsets.UTF_8)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
