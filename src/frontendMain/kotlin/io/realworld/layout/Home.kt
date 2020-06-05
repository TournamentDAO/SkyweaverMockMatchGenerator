package io.realworld.layout

import io.realworld.AppManager
import io.realworld.ConduitState
import pl.treksoft.kvision.core.*
import pl.treksoft.kvision.form.check.RadioGroupInput
import pl.treksoft.kvision.form.check.radioGroupInput
import pl.treksoft.kvision.form.fieldLabel
import pl.treksoft.kvision.form.form
import pl.treksoft.kvision.form.text.*
import pl.treksoft.kvision.html.*

fun Container.homePage(state: ConduitState) {
    div(className = "home-page") {
        div(className = "banner") {
            background = Background(Color.name(Col.BLACK))
            div(className = "container") {
                h1("Skyweaver Test Match Generator", className = "logo-font")
            }
        }
        div(className = "container page") {
            div(className = "row") {
                div(className = "col-md-12") {
                    h1("Generate Fake Match:", className = "text-xs-center")
                    if (!state.skyweaverMatchesErrors.isNullOrEmpty()) {
                        ul(state.skyweaverMatchesErrors, className = "error-messages")
                    }
                    lateinit var player1: TextInput
                    lateinit var player2: TextInput
                    lateinit var tournamentId: TextInput
                    lateinit var winner: RadioGroupInput
                    form {
                        fieldset {
                            fieldset(className = "form-group") {
                                player1 = textInput() {
                                    placeholder = "Player 1's Public Address"
                                }
                            }
                            fieldset(className = "form-group") {
                                player2 = textInput() {
                                    placeholder = "Player 2's Public Address"
                                }
                            }
                            fieldset(className = "form-group") {
                                tournamentId = textInput() {
                                    placeholder = "Tournament ID"
                                }
                            }
                            fieldset(className = "form-group") {
                                fieldLabel(forId = "selectWinner") {
                                    content = "Winner:"
                                }
                                winner = radioGroupInput(
                                        listOf("player1" to "Player 1", "player2" to "Player 2")
                                )
                            }
                        }
                        button(
                                "Submit", type = ButtonType.SUBMIT,
                                className = "btn-sm"
                        )
                    }.onEvent {
                        submit = { ev ->
                            ev.preventDefault()
                            AppManager.createSkyweaverMatch(
                                    player1.value,
                                    player2.value,
                                    tournamentId.value,
                                    winner.value
                            )
                        }
                    }
                    if (state.skyweaverMatchesLoading) {
                        div("Loading matches...", className = "article-preview")
                    } else if (!state.skyweaverMatches.isNullOrEmpty()) {
                        state.skyweaverMatches.forEach {
                            skyweaverMatchPreview(it)
                        }
                    } else {
                        div("No matches generated yet...", className = "article-preview")
                    }
                }
            }
        }
    }
}
