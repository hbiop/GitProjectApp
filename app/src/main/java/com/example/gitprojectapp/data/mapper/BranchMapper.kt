package com.example.gitprojectapp.data.mapper

import com.example.gitprojectapp.data.models.BranchDto
import com.example.gitprojectapp.data.models.FileDto
import com.example.gitprojectapp.domain.models.Branch
import com.example.gitprojectapp.domain.models.mFile

class BranchMapper {
    fun mapFromEntity(entity: BranchDto): Branch {
        return Branch(entity.name)
    }

    fun mapFromEntityList(entities: List<BranchDto>): List<Branch> {
        return entities.map { mapFromEntity(it) }
    }
}