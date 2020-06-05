package io.realworld

import io.realworld.layout.homePage
import pl.treksoft.kvision.Application
import pl.treksoft.kvision.html.main
import pl.treksoft.kvision.pace.Pace
import pl.treksoft.kvision.pace.PaceOptions
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.root
import pl.treksoft.kvision.require
import pl.treksoft.kvision.startApplication
import pl.treksoft.kvision.state.bind

class App : Application() {

    override fun start() {
        Pace.init(require("pace-progressbar/themes/green/pace-theme-bounce.css"))
        Pace.setOptions(PaceOptions(manual = true))
        AppManager.initialize()
        root("kvapp", containerType = ContainerType.NONE, addRow = false) {
            main {
                bind(AppManager.store) { state ->
                    if (!state.appLoading) {
                        homePage(state)
                    }
                }
            }
        }
    }
}

fun main() {
    startApplication(::App)
}
