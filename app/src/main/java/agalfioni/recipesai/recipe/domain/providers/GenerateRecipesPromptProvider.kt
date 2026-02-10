package agalfioni.recipesai.recipe.domain.providers

object GenerateRecipesPromptProvider {

    const val DEFAULT_RECIPE_NUMBER_TO_GENERATE = 4

    // Future task: use Firebase Remote Config

    fun generateRecipePrompt(
        language: String,
        ingredients: List<String>,
        recipesQty: Int? = null
    ): String {
        val ingredientsString = ingredients.joinToString(", ")
        return """
    You are a structured recipe generation engine.

    Task:
    Given this list of available ingredients ($ingredientsString), generate EXACTLY ${recipesQty ?: DEFAULT_RECIPE_NUMBER_TO_GENERATE} recipes.
    The response MUST be in the language specified: $language.

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
        "instructions": [
        {
            "title": string,
            "description": string,
        }
        ]
    ,
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

    Instructions rules:
    - If possible, each instruction step may include a visual or sensory cue.
    - Instructions must cover the full lifecycle of the dish.

    Validation:
    - ingredient_coverage must be logically consistent
    - Nutrition values must be realistic and non-negative
    - Instructions must be ordered and actionable
    """

    }

}