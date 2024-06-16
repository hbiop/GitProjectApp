package com.example.gitprojectapp.data.mapper

import com.example.gitprojectapp.data.models.GitRepositoryDto
import com.example.gitprojectapp.data.models.ReadmeDto
import com.example.gitprojectapp.domain.models.Readme
import com.example.gitprojectapp.domain.models.gitRepository

class ReadmeMapper {
    fun mapFromEntity(entity: ReadmeDto): Readme{
        return Readme(entity.content)
    }

    fun mapFromEntityList(entities: List<ReadmeDto>): List<Readme> {
        return entities.map { mapFromEntity(it) }
    }
}