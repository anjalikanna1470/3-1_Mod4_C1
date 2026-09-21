package mod4.case1

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("process")
    suspend fun processText(@Body request: LLMRequest): LLMResponse
}