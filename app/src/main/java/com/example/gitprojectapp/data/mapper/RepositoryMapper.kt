package com.example.gitprojectapp.data.mapper

import com.example.gitprojectapp.data.models.GitRepositoryDto
import com.example.gitprojectapp.domain.models.gitRepository

class RepositoryMapper {
    fun mapFromEntity(entity: GitRepositoryDto): gitRepository {
        return gitRepository(id = entity.id, name = entity.name, forksCount = entity.forksCount, watchersCount = entity.watchersCount, stargazersCount = entity.stargazersCount, license = entity.license, url = entity.url)
    }

    fun mapFromEntityList(entities: List<GitRepositoryDto>): List<gitRepository> {
        return entities.map { mapFromEntity(it) }
    }
}