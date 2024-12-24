package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.UpdateJdwViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateJdwView(
    onBack: () -> Unit = { },
    onNavigate: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: UpdateJdwViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiJdwState = viewModel.updateJdwUIState
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()



    LaunchedEffect(uiJdwState.snackbarMessage) {
        println("LaunchedEffect Triggered")
        uiJdwState.snackbarMessage?.let { message ->
            println("Snackbar Message Received: $message")
            coroutineScope.launch {
                println("Launching Coroutine For Snackbar")
                snackBarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                judul = "Edit Jadwal",
                showBackButton = true,
                onBack = onBack
            )
        }
    ){
            padding ->
        Column (
            modifier = Modifier.padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ){
            InsertBodyJadwal (
                JdwState = uiJdwState,
                onValueChange = { updatedJdwEvent ->
                    viewModel.updateJdwState(updatedJdwEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate()
                            }
                        }
                    }
                }
            )
        }
    }
}