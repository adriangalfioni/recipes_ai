package agalfioni.recipesai.recipe.data.local.typeconverters

import agalfioni.recipesai.recipe.domain.models.Difficulty
import androidx.room.TypeConverter

class RecipeConverters {
    @TypeConverter
    fun fromStringList(value: List<String>): String = value.joinToString(separator = "||")

    @TypeConverter
    fun toStringList(value: String): List<String> = value.split("||")

    @TypeConverter
    fun fromDifficulty(difficulty: Difficulty): String = difficulty.name

    @TypeConverter
    fun toDifficulty(value: String): Difficulty = enumValueOf<Difficulty>(value)
}
