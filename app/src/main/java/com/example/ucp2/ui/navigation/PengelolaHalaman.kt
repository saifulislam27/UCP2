package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dokter.DestinasiInsertDr
import com.example.ucp2.ui.view.dokter.HomeDktView
import com.example.ucp2.ui.view.dokter.InsertDktView
import com.example.ucp2.ui.view.jadwal.DestinasiInsertJad
import com.example.ucp2.ui.view.jadwal.DetailJadView
import com.example.ucp2.ui.view.jadwal.HomeJdwView
import com.example.ucp2.ui.view.jadwal.InsertJdwView
import com.example.ucp2.ui.view.jadwal.UpdateJdwView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = AlamatNavigasi.DestinasiHomeDkt.route) {
        composable(route = AlamatNavigasi.DestinasiHomeDkt.route) {
            HomeDktView(
                onAddDr = {
                    navController.navigate(DestinasiInsertDr.route)
                },
                onAddJad = {
                    navController.navigate(AlamatNavigasi.DestinasiHomeJdw.route)
                },
                modifier = modifier
            )
        }
        composable(route = DestinasiInsertDr.route) {
            InsertDktView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = AlamatNavigasi.DestinasiHomeJdw.route) {
            HomeJdwView(
                onBack = { navController.popBackStack() },
                onDetailClick = { id ->
                    navController.navigate("${AlamatNavigasi.DestinasiDetailJdw.route}/$id")
                },
                onAddJdw = {
                    navController.navigate(DestinasiInsertJad.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertJad.route
        ) {
            InsertJdwView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            AlamatNavigasi.DestinasiDetailJdw.routeWithArgs,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiDetailJdw.id) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(AlamatNavigasi.DestinasiDetailJdw.id)
            id?.let { id ->
                DetailJadView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${AlamatNavigasi.DestinasiEditJdw.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            AlamatNavigasi.DestinasiEditJdw.routeWithArgs,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiEditJdw.id) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateJdwView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}