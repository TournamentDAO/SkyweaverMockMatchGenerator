package io.realworld.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class SkyWeaverAPIMock(
        val res: List<SkyWeaverMatchMock>
)

@Serializable
data class SkyWeaverMatchMock(
        val id: Int,
        val status: String = "FORFEITED",
        val mode: String = "RANKED_RANDOM",
        val player1: PlayerData,
        val player2: PlayerData,
        val player1DeckClass: String = "STR",
        val player2DeckClass: String = "STR",
        val winningPlayer: Int,
        val turnNonce: Int = 1
)

@Serializable
data class PlayerData(
        val address: String,
        val name: String = "Test",
        val region: String? = null,
        val tagArtID: String = "bg-mind-02",
        val deckString: String = "SWxSTR015CiiGq7hrP2qkBNkGWdfgjuG1dGbpDVhRoew3J7f9BVJs4N7EnGi6V6vrTGMbq1Ggqk3MwnzSZWJzKDxk2c1nj7QGzn2jLDa6Y1wXpQKZz488AAUpsCDc8BC1L4GwjAc7suWCh5NKG2uDfzw5",
        val initDeckString: String = "SWxSTR01"
)

@Serializable
data class SkyWeaverRequestMock(
        val req: Map<String, String>
)
