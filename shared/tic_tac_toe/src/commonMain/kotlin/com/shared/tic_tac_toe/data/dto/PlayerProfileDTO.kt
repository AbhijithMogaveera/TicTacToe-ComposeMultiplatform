package com.shared.tic_tac_toe.data.dto

import arrow.core.None
import arrow.core.some
import com.shared.tic_tac_toe.domain.models.PlayerProfile
import com.shared.tic_tac_toe.domain.models.TileState
import kotlinx.serialization.Serializable

@Serializable
data class PlayerProfileDTO(
    val bio: String?,
    val profile_image: String?,
    val user_name: String,
    val isActive: Boolean,
    val tile: TileState
) {
    fun toPlayerDetails(): PlayerProfile {
        return PlayerProfile(
            bio = bio?.some() ?: None,
            profile_image = profile_image?.replace("localhost", "10.0.2.2")?.some() ?: None,
            user_name = user_name,
            isActive = isActive,
            tile = tile
        )
    }
}