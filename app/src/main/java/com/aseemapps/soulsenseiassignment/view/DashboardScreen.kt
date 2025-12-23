package com.aseemapps.soulsenseiassignment.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.aseemapps.soulsenseiassignment.utils.DashboardUiState
import com.aseemapps.soulsenseiassignment.viewmodel.DashboardViewmodel

@Composable
fun DashboardScreen(
    innerPadding: PaddingValues,
    viewModel: DashboardViewmodel = hiltViewModel()
) {
    when (val state = viewModel.uiState.value) {
        is DashboardUiState.Loading -> {
            LoadingView()
        }

        is DashboardUiState.Error -> {
            ErrorView(
                message = state.message,
                onRetry = { viewModel.fetchData() }
            )
        }

        is DashboardUiState.Success -> {
            DashboardContent(
                dashboardData = state.data,
                innerPadding = innerPadding
            )
        }
    }
}