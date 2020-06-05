package io.realworld

import io.realworld.model.SkyweaverMatch
import pl.treksoft.kvision.redux.RAction

data class ConduitState(
    val appLoading: Boolean = true,
    val view: View = View.HOME,
    val skyweaverMatchesLoading: Boolean = false,
    val skyweaverMatches: List<SkyweaverMatch>? = null,
    val skyweaverMatchesCount: Int = 0,
    val skyweaverMatch: SkyweaverMatch? = null,
    val skyweaverMatchesErrors: List<String>? = null
)

sealed class ConduitAction : RAction {
    object AppLoaded : ConduitAction()
    object HomePage : ConduitAction()

    object SkyweaverMatchesLoading : ConduitAction()
    data class SkyweaverMatchesLoaded(val skyweaverMatches: List<SkyweaverMatch>, val skyweaverMatchesCount: Int) : ConduitAction()
    data class SkyweaverMatchesError(val errors: List<String>) : ConduitAction()
}

fun conduitReducer(state: ConduitState, action: ConduitAction): ConduitState = when (action) {
    is ConduitAction.AppLoaded -> {
        state.copy(appLoading = false)
    }
    is ConduitAction.HomePage -> {
        state.copy(view = View.HOME, skyweaverMatches = null)
    }
    is ConduitAction.SkyweaverMatchesLoading -> {
        state.copy(skyweaverMatchesLoading = true)
    }
    is ConduitAction.SkyweaverMatchesLoaded -> {
        state.copy(skyweaverMatchesLoading = false, skyweaverMatches = action.skyweaverMatches, skyweaverMatchesCount = action.skyweaverMatchesCount)
    }
    is ConduitAction.SkyweaverMatchesError -> {
        state.copy(skyweaverMatchesErrors = action.errors)
    }
}
