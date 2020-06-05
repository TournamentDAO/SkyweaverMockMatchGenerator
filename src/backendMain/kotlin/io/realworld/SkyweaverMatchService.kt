package io.realworld

import io.realworld.dao.SkyweaverMatchDao
import io.realworld.model.*
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import pl.treksoft.kvision.remote.ServiceException
import java.time.OffsetDateTime

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
actual class SkyweaverMatchService(
        val skyweaverMatchDao: SkyweaverMatchDao
) : ISkyweaverMatchService {

    override suspend fun skyweaverMatches(offset: Int, limit: Int): SkyweaverMatchesDto {
        return skyweaverMatchDao.getSkyweaverMatches(offset, limit)
    }

    override suspend fun createSkyweaverMatch(player1: String?, player2: String?, tournamentId: String?, winner: Int?): SkyweaverMatch {
        val errorMessages = mutableListOf<String>()
        if (player1.isNullOrEmpty()) errorMessages += "player1 can't be blank"
        if (player2.isNullOrEmpty()) errorMessages += "player2 can't be blank"
        if (tournamentId.isNullOrEmpty()) errorMessages += "tournamentId can't be blank"
        if (winner == null) errorMessages += "winner can't be blank"
        if (errorMessages.isEmpty()) {
            skyweaverMatchDao.createSkyweaverMatch(
                    SkyweaverMatch(
                            player1 = player1,
                            player2 = player2,
                            tournamentId = tournamentId,
                            winner = winner,
                            createdAt = OffsetDateTime.now(),
                            updatedAt = OffsetDateTime.now()
                    )
            )?.let {
                return skyweaverMatch(it)
            } ?: throw ServiceException("error while saving match")
        } else {
            throw ServiceException(errorMessages.joinToString("\n"))
        }
    }

    override suspend fun skyweaverMatch(id: Int): SkyweaverMatch {
        return skyweaverMatchDao.getSkyweaverMatch(id) ?: throw ServiceException("match not found")
    }

    override suspend fun deleteSkyweaverMatch(id: Int) {
        val skyweaverMatch = skyweaverMatchDao.getSkyweaverMatch(id) ?: throw ServiceException("match not found")
        skyweaverMatchDao.deleteSkyweaverMatch(skyweaverMatch)
    }
}
