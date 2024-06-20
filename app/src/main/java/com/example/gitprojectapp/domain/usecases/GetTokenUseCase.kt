package com.example.gitprojectapp.domain.usecases

import com.example.gitprojectapp.domain.repository.SharedPreferenceRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val sharedPreferenceRepository: SharedPreferenceRepository) {
    fun execute(): String{
        return sharedPreferenceRepository.getToken()
    }
}