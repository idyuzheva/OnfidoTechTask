package com.onfido.techtask.cat.data

import com.onfido.techtask.cat.data.model.CatFact

class CatRepositoryImpl(
    private val memoryDataSource: CatMemoryDataSource,
    private val remoteDataSource: CatRemoteDataSource
) : CatRepository {

    override suspend fun loadFacts(): List<CatFact> {
        return if (memoryDataSource.data.isNotEmpty()) memoryDataSource.data else {
            remoteDataSource.getData().also {
                memoryDataSource.data = it
            }
        }
    }

    override suspend fun loadFacts(searchTerm: String): List<CatFact> {
        val filtered = arrayListOf<CatFact>()
        memoryDataSource.data.forEach {
            if (it.text.lowercase().contains(searchTerm.lowercase().trim())) {
                filtered.add(it)
            }
        }
        return filtered
    }
}