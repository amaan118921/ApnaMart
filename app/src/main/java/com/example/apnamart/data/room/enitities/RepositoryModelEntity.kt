package com.example.apnamart.data.room.enitities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apnamart.domain.models.OwnerModel
import com.example.apnamart.domain.models.RepositoryModel


@Entity(tableName = "repository_table")
data class RepositoryModelEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var login: String,
    @ColumnInfo
    var avatarUrl: String? = null,
    @ColumnInfo
    var description: String,
    @ColumnInfo
    var watchers: Int,
    @ColumnInfo
    var forks: Int,
    @ColumnInfo
    var score: Float,
    @ColumnInfo
    var language: String,
    @ColumnInfo
    var visible: Boolean = false
)

fun RepositoryModelEntity.toRepositoryModel(): RepositoryModel {
    return RepositoryModel(
        name = this.name,
        owner = OwnerModel(
            login = this.login,
            avatarUrl = this.avatarUrl
        ),
        description = this.description,
        watchers = this.watchers,
        forks = this.forks,
        score = this.score,
        language = this.language
    )
}
