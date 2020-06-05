package io.realworld

import io.realworld.dao.SkyweaverMatchDao
import io.realworld.model.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rpc")
actual class SkyweaverMatchAPIMockServiceController(
        val skyweaverMatchDao: SkyweaverMatchDao
) {
    @PostMapping("/SkyWeaverAPI/ListMatches")
    actual suspend fun skyweaverMatchesByUserAddress(@RequestBody req: SkyWeaverRequestMock): SkyWeaverAPIMock {
        return SkyWeaverAPIMock(
                res = req.req["accountAddress"]?.let {
                    skyweaverMatchDao.getSkyweaverMatchesByUserAddress(it).map { match ->
                        SkyWeaverMatchMock(
                                id = match.id!!,
                                player1 = PlayerData(address = match.player1!!),
                                player2 = PlayerData(address = match.player2!!),
                                winningPlayer = match.winner!!
                        )
                    }
                } ?: throw RuntimeException("Must supply account address")
        )
    }
}
