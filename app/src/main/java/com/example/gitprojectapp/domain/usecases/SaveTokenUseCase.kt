package com.example.gitprojectapp.domain.usecases

import com.example.gitprojectapp.domain.models.UserInfo
import com.example.gitprojectapp.domain.repository.SharedPreferenceRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val sharedPreferenceRepository: SharedPreferenceRepository) {
    fun execute(userInfo: UserInfo, token: String){
        sharedPreferenceRepository.saveToken(userInfo, token)
    }
}