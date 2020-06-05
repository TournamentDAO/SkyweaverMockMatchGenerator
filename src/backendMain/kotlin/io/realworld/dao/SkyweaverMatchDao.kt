package io.realworld.dao

import com.github.andrewoma.kwery.core.builder.query
import io.r2dbc.spi.ConnectionFactory
import io.realworld.model.SkyweaverMatch
import io.realworld.model.SkyweaverMatchesDto
import io.realworld.utils.bindMap
import io.realworld.utils.withTransaction
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitOne
import org.springframework.data.r2dbc.core.awaitOneOrNull
import org.springframework.data.r2dbc.core.flow
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.stereotype.Service

val logger: Logger = LoggerFactory.getLogger(SkyweaverMatchDao::class.java)

@Service
class SkyweaverMatchDao(val connectionFactory: ConnectionFactory, val databaseClient: DatabaseClient) {

    suspend fun getSkyweaverMatches(offset: Int, limit: Int): SkyweaverMatchesDto {
        val query = query {
            select("SELECT * FROM skyweaver_matches")
            orderBy("created_at DESC LIMIT $limit OFFSET $offset")
        }
        val skyweaverMatches = databaseClient.execute(query.sql).bindMap(query.parameters)
                .`as`(SkyweaverMatch::class.java).fetch().flow().toList()
        val queryCount = query {
            select("SELECT COUNT(*) as count FROM skyweaver_matches")
        }
        val skyweaverMatchesCount = (databaseClient.execute(queryCount.sql).bindMap(queryCount.parameters)
                .fetch().awaitOne()["count"] as Long).toInt()
        logger.error("Count: ${skyweaverMatchesCount}")
        return SkyweaverMatchesDto(skyweaverMatches, skyweaverMatchesCount)
    }

    suspend fun getSkyweaverMatchesByUserAddress(userAddress: String): List<SkyweaverMatch> {
        return databaseClient.select().from(SkyweaverMatch::class.java)
                .matching(where("player1").`is`(userAddress).or(where("player2").`is`(userAddress)))
                .orderBy(Sort.by(Sort.Direction.DESC, "created_at"))
                .fetch().flow().toList()
    }

    suspend fun getSkyweaverMatch(id: Int): SkyweaverMatch? {
        return databaseClient.select().from(SkyweaverMatch::class.java).matching(where("id").`is`(id)).fetch()
                .awaitOneOrNull()
    }

    suspend fun createSkyweaverMatch(skyweaverMatch: SkyweaverMatch): Int? {
        logger.error(skyweaverMatch.toString())
        return withTransaction(connectionFactory) {
            val skyweaverMatchId = databaseClient.insert().into(SkyweaverMatch::class.java).using(skyweaverMatch).map { row ->
                row.get("id") as Int
            }.awaitOne()
            skyweaverMatchId
        }
    }

    suspend fun deleteSkyweaverMatch(skyweaverMatch: SkyweaverMatch) {
        withTransaction(connectionFactory) {
            databaseClient.delete().from(SkyweaverMatch::class.java).matching(where("id").`is`(skyweaverMatch.id!!)).fetch()
                    .rowsUpdated().awaitSingle()
        }
    }
}
