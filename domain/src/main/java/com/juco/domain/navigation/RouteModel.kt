package com.juco.domain.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {

    @Serializable
    data object Home : RouteModel

    @Serializable
    data object Calendar : RouteModel
}