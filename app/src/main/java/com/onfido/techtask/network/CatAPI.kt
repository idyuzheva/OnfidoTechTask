package com.onfido.techtask.network

import com.onfido.techtask.cat.data.model.CatFact
import retrofit2.http.GET

interface CatAPI {

    @GET("b/6064467b418f307e2585ef1b")
    suspend fun getFacts(): List<CatFact>
}