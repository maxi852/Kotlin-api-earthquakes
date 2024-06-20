package com.example.kotlinapiter

import com.example.kotlinapiter.model.Earthquakes
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET
    suspend fun getSignificantQuakesByMonth() : Response<Earthquakes>

    @GET
    suspend fun getAllQuakesByDay() : Response<Earthquakes>
}