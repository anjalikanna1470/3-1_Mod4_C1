package mod4.case1

data class LLMResponse(
    val original: String,
    val summary: String,
    val category: String,
    val rewritten: String
)