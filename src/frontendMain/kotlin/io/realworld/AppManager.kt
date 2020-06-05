package io.realworld

import io.realworld.helpers.withProgress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import pl.treksoft.kvision.redux.createReduxStore

object AppManager : CoroutineScope by CoroutineScope(Dispatchers.Default + SupervisorJob()) {

    val store = createReduxStore(::conduitReducer, ConduitState())
    val skyweaverMatchService = SkyweaverMatchService()

    fun initialize() {
        store.dispatch(ConduitAction.AppLoaded)
        if (store.getState().view == View.HOME) {
            loadSkyweaverMatches()
        }
    }

    fun createSkyweaverMatch(player1: String?, player2: String?, tournamentId: String?, winner: String?) {
        val winnerInt = when (winner) {
            "player1" -> 1
            "player2" -> 2
            else -> throw Error("Winner must be player1 or player2")
        }
        withProgress {
            try {
                skyweaverMatchService.createSkyweaverMatch(player1, player2, tournamentId, winnerInt)
                loadSkyweaverMatches()
            } catch (e: Exception) {
                store.dispatch(ConduitAction.SkyweaverMatchesError(parseErrors(e.message)))
            }
        }
    }

    fun homePage() {
        store.dispatch(ConduitAction.HomePage)
        val state = store.getState()
        if (!state.appLoading) {
            loadSkyweaverMatches()
        }
    }

    private fun loadSkyweaverMatches() {
        store.dispatch(ConduitAction.SkyweaverMatchesLoading)
        withProgress {
            val limit = 10
            val offset = 0
            val skyweaverMatchesDto = skyweaverMatchService.skyweaverMatches(offset, limit)
            store.dispatch(ConduitAction.SkyweaverMatchesLoaded(skyweaverMatchesDto.skyweaverMatches, skyweaverMatchesDto.skyweaverMatchesCount))
        }
    }

    private fun parseErrors(message: String?): List<String> {
        return message?.split("\n")?.toList() ?: emptyList()
    }
}
