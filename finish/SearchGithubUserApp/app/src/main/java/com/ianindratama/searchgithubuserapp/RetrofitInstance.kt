package com.ianindratama.searchgithubuserapp

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.github.com/"

class RetrofitInstance {

    companion object {
        private val retrofit by lazy {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val client = OkHttpClient.Builder()
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

        val API_OBJECT: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }

}