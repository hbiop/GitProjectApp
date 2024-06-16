package com.example.gitprojectapp.data.mapper

import com.example.gitprojectapp.data.models.UserInfoDto
import com.example.gitprojectapp.domain.models.UserInfo
import javax.inject.Inject

class UserMapper{
    fun mapFromEntity(entity: UserInfoDto): UserInfo {
        return UserInfo(entity.id, entity.login)
    }

    fun mapFromEntityList(entities: List<UserInfoDto>): List<UserInfo> {
        return entities.map { mapFromEntity(it) }
    }
}