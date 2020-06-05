package io.realworld

import pl.treksoft.navigo.Navigo

enum class View(val url: String) {
    HOME("/")
}

fun Navigo.initialize(): Navigo {
    return on(View.HOME.url, { _ ->
        AppManager.homePage()
    })
}
