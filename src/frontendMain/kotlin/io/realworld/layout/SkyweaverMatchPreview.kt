package io.realworld.layout

import io.realworld.model.SkyweaverMatch
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.html.*
import pl.treksoft.kvision.types.toStringF

fun Container.skyweaverMatchPreview(skyweaverMatch: SkyweaverMatch) {
    div(className = "article-preview") {
        div(className = "article-meta") {
            link("${skyweaverMatch.player1} vs ${skyweaverMatch.player2}", "#/@${skyweaverMatch.id}")
            div(className = "info") {
                link("Tournament: ${skyweaverMatch.tournamentId}","", "#/@${skyweaverMatch.tournamentId}", className = "author")
                val createdAtFormatted = skyweaverMatch.createdAt?.toStringF("MMMM D, YYYY")
                span(createdAtFormatted, className = "date")
                span("winner: ${skyweaverMatch.winner}", className = "date")
            }
        }
    }
}
