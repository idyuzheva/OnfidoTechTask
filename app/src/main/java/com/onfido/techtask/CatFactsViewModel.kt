package com.onfido.techtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onfido.techtask.cat.data.model.CatFact
import com.onfido.techtask.cat.usecase.GetCatFactsUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CatFactsViewModel(private val getCatFactsUseCase: GetCatFactsUseCase) :
    ViewModel() {

    private val _mutableCatFacts = MutableLiveData<List<CatFact>>(emptyList())
    private val _mutableLoadingState = MutableLiveData(false)
    private val _mutableErrorState = MutableLiveData(-1)

    val catFacts: LiveData<List<CatFact>> get() = _mutableCatFacts
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState
    val errorState: LiveData<Int> get() = _mutableErrorState

    init {
        loadCatFacts()
    }

    private fun loadCatFacts() {
        viewModelScope.launch {
            try {
                _mutableErrorState.value = -1
                _mutableLoadingState.value = true
                _mutableCatFacts.value = getCatFactsUseCase.loadCatFacts()
                _mutableLoadingState.value = false
            } catch (throwable: Throwable) {
                val errorTextId = when (throwable) {
                    is IOException, is HttpException -> R.string.state_network_error
                    else -> R.string.state_common_error
                }
                _mutableErrorState.value = errorTextId
            }
        }
    }

    fun onUserSearch(searchTerm: String) {
        viewModelScope.launch {
            _mutableErrorState.value = -1
            _mutableCatFacts.value = getCatFactsUseCase.loadCatFacts(searchTerm)
        }
    }
}