package com.onfido.techtask.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onfido.techtask.CatFactsViewModel
import com.onfido.techtask.cat.data.CatMemoryDataSource
import com.onfido.techtask.cat.data.CatRemoteDataSource
import com.onfido.techtask.cat.data.CatRepositoryImpl
import com.onfido.techtask.cat.usecase.GetCatFactsUseCase
import com.onfido.techtask.network.NetworkModule

class AppViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        CatFactsViewModel::class.java -> CatFactsViewModel(
            GetCatFactsUseCase(
                CatRepositoryImpl(
                    CatMemoryDataSource(),
                    CatRemoteDataSource(NetworkModule.create())
                )
            )
        )
        else -> throw IllegalArgumentException("$modelClass is unknown ViewModel")
    } as T
}