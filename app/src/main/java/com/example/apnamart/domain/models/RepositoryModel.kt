package com.example.apnamart.domain.models

import com.example.apnamart.data.room.enitities.RepositoryModelEntity

data class RepositoryModel(
    var name: String? = null,
    var owner: OwnerModel? = null,
    var description: String? = null,
    var watchers: Int? = null,
    var forks: Int? = null,
    var score: Float? = null,
    var language: String? = null,
    var visible: Boolean = false
)


fun RepositoryModel.toRepositoryModelEntity(): RepositoryModelEntity {
    return RepositoryModelEntity(
        name = this.name ?: "",
        login = this.owner?.login ?: "",
        avatarUrl = owner?.avatarUrl,
        description = this.description ?: "",
        watchers = this.watchers ?: 0,
        forks = this.forks ?: 0,
        score = this.score ?: 0f,
        language = this.language ?: ""
    )
}