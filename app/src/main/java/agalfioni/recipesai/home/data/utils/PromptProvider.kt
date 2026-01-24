package agalfioni.recipesai.home.data.utils

object PromptProvider {

    // Future task: use Firebase Remote Config

    const val FRIDGE_ANALYZER_PROMPT = """
You are an expert food recognition system.

Analyze the image of a refrigerator interior.
Identify ONLY food items that are clearly visible.

Return ONLY valid JSON using this schema:
{
  "vegetables": string[],
  "fruits": string[],
  "dairy": string[],
  "meat": string[],
  "drinks": string[],
  "other": string[]
}

Rules:
- Be specific (e.g. "broccoli", not "vegetable")
- Do not guess or hallucinate
- If unsure, omit the item
- Empty categories must be empty arrays
- No markdown
- No explanations
"""
}