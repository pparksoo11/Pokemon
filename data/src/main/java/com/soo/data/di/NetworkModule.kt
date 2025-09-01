package com.soo.data.di

import com.soo.data.remote.interceptor.CurlLoggingInterceptor
import com.soo.data.service.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT = 30L

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HttpsClient

    @Provides
    @Singleton
    fun provideCurlLoggingInterceptor(): CurlLoggingInterceptor = CurlLoggingInterceptor()

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @HttpClient
    fun provideHttpClient(
        logging: HttpLoggingInterceptor,
        curlLoggingInterceptor: CurlLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(curlLoggingInterceptor)
            .protocols(listOf(Protocol.HTTP_1_1))
            .build()
    }

    /**
     * HTTPS 전용 클라이언트
     * */
    @Provides
    @Singleton
    @HttpsClient
    fun provideHttpsClient(
        logging: HttpLoggingInterceptor,
        curlLoggingInterceptor: CurlLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(curlLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    @Named("pokeRetrofit")
    fun providePokeRetrofit(
        @HttpsClient client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providePokeService(@Named("pokeRetrofit") retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }
}