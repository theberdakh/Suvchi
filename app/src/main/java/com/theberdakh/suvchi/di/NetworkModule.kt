package com.theberdakh.suvchi.di

import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import com.theberdakh.suvchi.data.remote.LoginApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {


    fun provideRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )


        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain: Interceptor.Chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", LocalPreferences().accessToken)
                    .build()

                chain.proceed(newRequest)
            }
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.smartwaterdegree.uz")
            .client(client)
            .build()
    }

    single {
        provideRetrofit()
    }

    single<LoginApi> {
        get<Retrofit>().create(LoginApi::class.java)
    }


}