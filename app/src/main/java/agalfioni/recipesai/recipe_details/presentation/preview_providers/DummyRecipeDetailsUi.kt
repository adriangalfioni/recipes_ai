package agalfioni.recipesai.recipe_details.presentation.preview_providers

import agalfioni.recipesai.recipe_details.presentation.models.Ingredient
import agalfioni.recipesai.recipe_details.presentation.models.Instruction
import agalfioni.recipesai.recipe_details.presentation.models.PositionInList
import agalfioni.recipesai.recipe_details.presentation.models.RecipeDetailsUi

val dummyRecipeDetailsUi = RecipeDetailsUi(
    title = "Lemon Herb Roasted Chicken & Potatoes",
    minutesTime = "00:45",
    category = "Vegan",
    totalCalories = "550 kcal",
    aiScore = 80,
    chefInsight = null,
    ingredients = listOf(
        Ingredient("Chicken Breast", "500g"),
        Ingredient("Potatoes", "400g"),
        Ingredient("Olive Oil", "2 tablespoon"),
        Ingredient("Lemon Juice", "1 tablespoon"),
        Ingredient("Dried Oregano", "0.5 teaspoon"),
        Ingredient("Salt", "0.25 teaspoon"),
        Ingredient("Black Pepper", "0.25 teaspoon"),
        Ingredient("Fresh Parsley", "1 tablespoon"),
    ),
    instructions = listOf(
        Instruction(1, "Step 1", "Preheat oven to 200°C (400°F).", PositionInList.START),
        Instruction(2, "Step 2", "Cut potatoes into 2-inch chunks and chicken breasts into similar size pieces if desired, or leave whole."),
        Instruction(3, "Step 3", "In a large bowl, toss potatoes and chicken with olive oil, lemon juice, dried oregano, salt, and black pepper."),
        Instruction(4, "Step 4", "Spread the mixture in a single layer on a baking sheet."),
        Instruction(5, "Step 5", "Roast for 30-35 minutes, or until chicken is cooked through and potatoes are tender and golden brown, flipping halfway."),
        Instruction(6, "Step 6", "Serve hot, garnished with fresh parsley if desired.", PositionInList.END)
    )
)