package com.protectly.pruebasllamadohttp.models

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): UserGithub
}