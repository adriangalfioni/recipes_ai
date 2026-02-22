package agalfioni.recipesai.recipe.di

import agalfioni.recipesai.BuildConfig
import agalfioni.recipesai.recipe.data.remote.TastyApiService
import agalfioni.recipesai.recipe.data.remote.interceptors.RapidApiInterceptor
import agalfioni.recipesai.recipe.data.remote.interceptors.provideLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    // Logging
    single { provideLoggingInterceptor() }

    // Rapid API interceptor
    single {
        RapidApiInterceptor(
            apiKey = BuildConfig.RAPID_API_KEY
        )
    }

    // OkHttp
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<RapidApiInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://tasty.p.rapidapi.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API Service
    single<TastyApiService> {
        get<Retrofit>().create(TastyApiService::class.java)
    }
}
