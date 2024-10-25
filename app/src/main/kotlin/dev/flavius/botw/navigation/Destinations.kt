package dev.flavius.botw.navigation

import kotlinx.serialization.Serializable

@Serializable object Permissions
@Serializable object Main
@Serializable data class Webview(val speciesPage: String)
