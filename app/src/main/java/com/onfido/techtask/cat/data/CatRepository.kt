package com.onfido.techtask.cat.data

import com.onfido.techtask.cat.data.model.CatFact

interface CatRepository {

    suspend fun loadFacts(): List<CatFact>

    suspend fun loadFacts(searchTerm: String): List<CatFact>
}