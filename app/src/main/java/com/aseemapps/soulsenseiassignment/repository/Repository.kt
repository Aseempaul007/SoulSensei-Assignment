package com.aseemapps.soulsenseiassignment.repository

import com.aseemapps.soulsenseiassignment.data.model.DashboardApiRespose
import com.aseemapps.soulsenseiassignment.data.remote.DashboardApi
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: DashboardApi
){
    suspend fun getDashboardData(
    ): DashboardApiRespose {
        val response = api.getDashboardData()
        var dashboardData: DashboardApiRespose? = null
        if (response.isSuccessful && response.body() != null) {
            dashboardData = response.body()!!
        }
        return dashboardData!!
    }
}