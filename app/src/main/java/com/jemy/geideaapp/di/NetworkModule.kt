package com.jemy.geideaapp.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jemy.geideaapp.BuildConfig
import com.jemy.geideaapp.data.network.Api
import com.jemy.geideaapp.utils.Constants
import com.jemy.geideaapp.utils.NetworkInterceptor
import com.jemy.geideaapp.utils.NullOnEmptyConverterFactory
import com.jemy.geideaapp.utils.extensions.isNetworkAvailable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun getOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: NetworkInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(networkInterceptor)
            .addInterceptor { chain -> buildHeaders(chain, context) }
            .build()
    }

    private fun buildHeaders(chain: Interceptor.Chain, context: Context): Response {
        val builder = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
        return chain.proceed(builder.build())
    }

    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    @Singleton
    fun getNetworkInterceptor(
        @ApplicationContext context: Context
    ): NetworkInterceptor = object : NetworkInterceptor() {
        override fun isInternetAvailable(): Boolean = context.isNetworkAvailable()
    }

    @Provides
    @Singleton
    fun getRetrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    @Provides
    @Singleton
    fun providesApiInterface(retrofit: Retrofit): Api = retrofit.create(
        Api::class.java
    )
}