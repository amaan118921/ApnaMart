package com.example.apnamart.data.repositories

import com.example.apnamart.domain.models.RepositoryResponseModel
import com.example.apnamart.domain.repostiory.AppRepository
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class FakeAppRepositoryImpl @Inject constructor() : AppRepository {
    private var success = true
    private var responseModelNull = false
    private var exception = false
    override suspend fun fetchRepos(query: String): Response<RepositoryResponseModel> {
        if (exception) throw IOException()
        if (success) {
            if (responseModelNull) return Response.success(null)
            return Response.success(RepositoryResponseModel(items = emptyList()))
        }
        return Response.error(404, ResponseBody.create(null, ""))
    }

    fun shouldBeSuccessful(value: Boolean) {
        success = value
    }

    fun responseModelNull(boolean: Boolean) {
        responseModelNull = boolean
    }

    fun setException(boolean: Boolean) {
        exception = boolean
    }
}