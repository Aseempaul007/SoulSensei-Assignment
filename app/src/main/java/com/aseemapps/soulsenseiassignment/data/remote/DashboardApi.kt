package com.aseemapps.soulsenseiassignment.data.remote

import com.aseemapps.soulsenseiassignment.data.model.DashboardApiRespose
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DashboardApi {


    @GET("assignment.json")
    suspend fun getDashboardData(): Response<DashboardApiRespose>

}