package com.example.apnamart.domain.models

import com.squareup.moshi.Json

data class OwnerModel(
    var login: String? = null, @Json(name = "avatar_url") var avatarUrl: String? = null
)
