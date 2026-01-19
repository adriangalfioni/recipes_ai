package agalfioni.recipesai.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor


val ktorClient = HttpClient(OkHttp) {

    // 1. Configure the OkHttp Engine specifically
    engine {
        // A. Add Standard OkHttp Interceptors
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        // B. Add Network Interceptors
        // addNetworkInterceptor(...)

        // C. Configure the OkHttp Builder directly
        config {
            followRedirects(true)
            retryOnConnectionFailure(true)
            // connectTimeout(10, TimeUnit.SECONDS) // Ktor handles timeouts, but you can set them here too
        }

        // D. Use an existing OkHttpClient instance (Optional)
        // preconfigured = myExistingOkHttpClientInstance
    }

    // 2. Standard Ktor Config (Same as before)
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }

    install(Logging)
}