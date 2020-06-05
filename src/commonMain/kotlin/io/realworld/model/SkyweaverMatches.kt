@file:ContextualSerialization(OffsetDateTime::class)

package io.realworld.model

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import pl.treksoft.kvision.remote.Column
import pl.treksoft.kvision.remote.Id
import pl.treksoft.kvision.remote.Table
import pl.treksoft.kvision.types.OffsetDateTime

@Serializable
data class SkyweaverMatchesDto(val skyweaverMatches: List<SkyweaverMatch>, val skyweaverMatchesCount: Int)

@Serializable
@Table("skyweaver_matches")
data class SkyweaverMatch(
        @Id
        val id: Int? = null,
        val player1: String? = null,
        val player2: String? = null,
        @Column("tournamentId") val tournamentId: String? = null,
        val winner: Int? = null,
        val createdAt: OffsetDateTime? = null,
        val updatedAt: OffsetDateTime? = null
)
