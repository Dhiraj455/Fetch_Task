package com.example.fetch_task.data

import retrofit2.http.GET

interface HiringApiInterface {
    @GET("hiring.json")
    suspend fun getHiringData() : List<Hiring>
}