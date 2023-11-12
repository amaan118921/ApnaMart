package com.example.apnamart.domain.models

import com.squareup.moshi.Json

data class RepositoryResponseModel(
    @Json(name="total_count") var totalCount: Int? = null, var items: List<RepositoryModel>? = null
)

