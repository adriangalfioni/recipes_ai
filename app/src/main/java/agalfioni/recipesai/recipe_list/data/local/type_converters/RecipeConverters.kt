package agalfioni.recipesai.recipe_list.data.local.type_converters

import agalfioni.recipesai.recipe_list.domain.models.Difficulty
import androidx.room.TypeConverter

class RecipeConverters {
    @TypeConverter
    fun fromStringList(value: List<String>): String = value.joinToString(separator = "||")

    @TypeConverter
    fun toStringList(value: String): List<String> = value.split("||")

    @TypeConverter
    fun fromDifficulty(difficulty: Difficulty): String {
        return difficulty.name
    }

    @TypeConverter
    fun toDifficulty(value: String): Difficulty {
        return enumValueOf<Difficulty>(value)
    }
}