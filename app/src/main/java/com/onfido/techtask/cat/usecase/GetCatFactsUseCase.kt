package com.onfido.techtask.cat.usecase

import com.onfido.techtask.cat.data.CatRepository
import com.onfido.techtask.cat.data.model.CatFact

class GetCatFactsUseCase(private val repository: CatRepository) {

    suspend fun loadCatFacts(): List<CatFact> {
        return repository.loadFacts()
    }

    suspend fun loadCatFacts(searchTerm: String): List<CatFact> {
        return repository.loadFacts(searchTerm)
    }
}