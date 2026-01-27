package agalfioni.recipesai.recipe_list.data.utils

object GenerateRecipesPromptProvider {

    const val RECIPE_NUMBER_TO_GENERATE = 4

    // Future task: use Firebase Remote Config

    fun generateRecipePrompt(
        ingredients: List<String>,
        recipesQty: Int? = null
    ): String {
        val ingredientsString = ingredients.joinToString(", ")
        return """
    You are a structured recipe generation engine.

    Task:
    Given this list of available ingredients ($ingredientsString), generate EXACTLY ${recipesQty ?: RECIPE_NUMBER_TO_GENERATE} recipes.

    Primary goal:
    - When number of ingredients is 3 or less then maximize usage of the provided ingredients
    - When number of ingredients is more than 3, is ok to use less ingredients.

    Ingredient rules:
    - Recipes MAY include additional ingredients not listed.
    - Prefer common, realistic ingredients.
    - Do not invent unusual or obscure items.
    - Normalize ingredient names consistently.

    Output rules:
    - Output VALID JSON ONLY.
    - Return the result as a raw JSON array.
    - Do NOT wrap the JSON in ```json fences.
    - Do NOT include any text outside the JSON.
    - No explanations, markdown, or comments.
    

    Output format:
    Return a JSON array, each object MUST strictly follow this schema:

    {
        "title": string,
        "difficulty": "easy" | "moderate" | "elaborated",
        "minutes_time": number
        "ingredient_coverage": number,
        "instructions": string[],
        "nutrition": {
        "calories": number,
    },
        "ingredients": [
        {
            "name": string,
            "quantity": number,
            "unit": string
        }
        ]
    }

    Difficulty rules:
    - easy → few steps, basic techniques
    - moderate → multiple steps, some preparation
    - elaborated → longer process or advanced techniques

    Ingredient coverage rules:
    - ingredient_coverage = percentage (0–100) of provided ingredients used
    - Coverage refers ONLY to the provided ingredient list

    Confidence rules:
    - confidence correlates with ingredient_coverage
    - confidence ≥ 0.8 when ingredient_coverage ≥ 80
    - lower coverage → lower confidence

    Validation:
    - ingredient_coverage and confidence must be logically consistent
    - Nutrition values must be realistic and non-negative
    - Instructions must be ordered and actionable
    """

    }

    /*"total_fat": number,
    "saturated_fat": number,
    "protein": number,
    "cholesterol": number,
    "sugars": number,
    "total_carbohydrate": number*/

}

