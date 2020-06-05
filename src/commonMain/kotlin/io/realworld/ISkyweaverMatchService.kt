package io.realworld

import io.realworld.model.SkyweaverMatch
import io.realworld.model.SkyweaverMatchesDto
import pl.treksoft.kvision.annotations.KVBindingRoute
import pl.treksoft.kvision.annotations.KVService

@KVService
interface ISkyweaverMatchService {

    @KVBindingRoute("listMatches")
    suspend fun skyweaverMatches(
        offset: Int = 0,
        limit: Int = 10
    ): SkyweaverMatchesDto

    @KVBindingRoute("createMatch")
    suspend fun createSkyweaverMatch(player1: String?, player2: String?, tournamentId: String?, winner: Int?): SkyweaverMatch
    @KVBindingRoute("getMatch")
    suspend fun skyweaverMatch(id: Int): SkyweaverMatch
    @KVBindingRoute("deleteMatch")
    suspend fun deleteSkyweaverMatch(id: Int)
}
