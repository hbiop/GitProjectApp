package com.example.gitprojectapp.data.mapper

import com.example.gitprojectapp.data.models.GitRepositoryDto
import com.example.gitprojectapp.data.models.UserInfoDto
import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.models.gitRepository
import com.example.gitprojectapp.other.repository.GitRepository

class RepositoryMapper {
    fun mapFromEntity(entity: GitRepositoryDto): gitRepository {
        return gitRepository(Id = entity.id, Name = entity.name, ForksCount = entity.forks_count, WatchersCount = entity.watchers_count, StargazersCount = entity.stargazers_count, License = entity.license, Url = entity.url)
    }

    fun mapFromEntityList(entities: List<GitRepositoryDto>): List<gitRepository> {
        return entities.map { mapFromEntity(it) }
    }
}