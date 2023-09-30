package com.ianindratama.searchgithubuserapp

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    suspend fun getSearchData(
        @Query("q") id: String
    ): Response<SearchResponse>

}