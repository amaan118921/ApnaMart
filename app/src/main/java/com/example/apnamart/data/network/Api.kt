package com.example.apnamart.data.network

import com.example.apnamart.domain.models.RepositoryResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("repositories")
    suspend fun fetchRepos(
        @Query("q") query: String
    ): Response<RepositoryResponseModel>
}