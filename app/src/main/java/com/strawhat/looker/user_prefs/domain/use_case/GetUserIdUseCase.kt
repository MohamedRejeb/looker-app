package com.strawhat.looker.user_prefs.domain.use_case

import com.strawhat.looker.user_prefs.domain.repository.UserPreferencesRepository
import com.strawhat.looker.utils.error_handling.UnauthorizedException
import javax.inject.Inject
import kotlin.jvm.Throws

class GetUserIdUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    @Throws(UnauthorizedException::class)
    suspend operator fun invoke(): String {
        return userPreferencesRepository.getUserId()
    }

}