package com.example.gitprojectapp.presentation.usecases

import com.example.gitprojectapp.domain.repository.SharedPreferenceRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val sharedPreferenceRepository: SharedPreferenceRepository) {
    fun execute(token: String){
        sharedPreferenceRepository.saveToken(token)
    }
}