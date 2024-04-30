package com.israel.israellab8empmanager

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitClient {

    //Base URL must end in /
    //private const val BASE_URL = "http://192.168.43.11:3000/"
    private const val BASE_URL = "https://manuel09434.pythonanywhere.com/"



    fun getClient(applicationContext: Context): Retrofit {
        AuthorizationInterceptor.create(applicationContext)

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthorizationInterceptor)
            .addInterceptor(RequestInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}

object RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        println("Outgoing request to ${request.url}")
        return chain.proceed(request)
    }
}

object AuthorizationInterceptor: Interceptor {
    private lateinit var applicationContext: Context
    fun create(applicationContext: Context) {
        this.applicationContext = applicationContext
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken =AuthUtils.getAccessToken(applicationContext)
        val requestWithHeader = chain.request()
            .newBuilder()
            .header(
                "Authorization", "Bearer $accessToken"
            )
            .build()

        return chain.proceed(requestWithHeader)
    }
}