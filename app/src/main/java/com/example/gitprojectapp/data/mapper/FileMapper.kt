package com.example.gitprojectapp.data.mapper

import com.example.gitprojectapp.data.models.FileDto
import com.example.gitprojectapp.data.models.ReadmeDto
import com.example.gitprojectapp.domain.models.Readme
import com.example.gitprojectapp.domain.models.mFile

class FileMapper {
    fun mapFromEntity(entity: FileDto): mFile {
        return mFile(entity.name, entity.path, entity.type)
    }

    fun mapFromEntityList(entities: List<FileDto>): List<mFile> {
        return entities.map { mapFromEntity(it) }
    }
}