package agalfioni.recipesai.recipe.data.models

sealed class SyncResult {
    data class Success(
        val count: Int,
    ) : SyncResult()

    data class PartialSuccess(
        val synced: Int,
        val total: Int,
    ) : SyncResult()

    data class Error(
        val message: String,
    ) : SyncResult()
}
