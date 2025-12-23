package com.aseemapps.soulsenseiassignment.utils

import com.aseemapps.soulsenseiassignment.data.model.DashboardApiRespose

sealed interface DashboardUiState {

    object Loading : DashboardUiState

    data class Success(
        val data: DashboardApiRespose
    ) : DashboardUiState

    data class Error(
        val message: String
    ) : DashboardUiState
}