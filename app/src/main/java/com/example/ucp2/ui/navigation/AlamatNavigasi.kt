package com.example.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String

    object DestinasiHomeDkt : AlamatNavigasi {
        override val route: String = "home"
    }

    object DestinasiHomeJdw : AlamatNavigasi {
        override val route = "jadwal"
    }
    object DestinasiDetailJdw : AlamatNavigasi {
        override val route = "detail"
        const val id = "id"
        val routeWithArgs = "$route/{$id}"
    }

    object DestinasiUpdateJdw : AlamatNavigasi {
        override val route = "update"
        const val id = "id"
        val routeWithArgs = "$route/{$id}"
    }

    object DestinasiEditJdw : AlamatNavigasi {
        override val route = "edit"
        const val id = "id"
        val routeWithArgs = "$route/{$id}"
    }
}