package com.onfido.techtask.cat.data

import com.onfido.techtask.cat.data.model.CatFact
import com.onfido.techtask.network.CatAPI

class CatRemoteDataSource(private val api: CatAPI) {

    suspend fun getData(): List<CatFact> {
        return api.getFacts()
    }
}