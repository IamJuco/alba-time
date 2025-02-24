package com.juco.domain.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {
    @Serializable
    data object WorkPlaceAdder : RouteModel

    @Serializable
    data class WorkPlaceDetail(val workPlaceId: Int) : RouteModel
}

sealed interface MainMenuRoute : RouteModel {
    @Serializable
    data object Home : RouteModel

    @Serializable
    data object Calendar : RouteModel
}