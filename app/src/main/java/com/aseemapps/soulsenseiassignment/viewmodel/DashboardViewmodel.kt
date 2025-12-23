package com.aseemapps.soulsenseiassignment.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aseemapps.soulsenseiassignment.data.model.DashboardApiRespose
import com.aseemapps.soulsenseiassignment.data.model.RailPageFaq
import com.aseemapps.soulsenseiassignment.repository.Repository
import com.aseemapps.soulsenseiassignment.utils.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State

@HiltViewModel
class DashboardViewmodel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState =
        mutableStateOf<DashboardUiState>(DashboardUiState.Loading)
    val uiState: State<DashboardUiState> = _uiState

    private val _faqList = mutableStateListOf<RailPageFaq>()
    val faqList: List<RailPageFaq> get() = _faqList

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            try {
                val response = repository.getDashboardData()

                _faqList.clear()
                _faqList.addAll(response.data.railPageFaqs)

                _uiState.value = DashboardUiState.Success(response)

            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(
                    e.localizedMessage ?: "Something went wrong"
                )
            }
        }
    }

    fun onFaqClicked(index: Int) {
        _faqList[index] = _faqList[index].copy(
            isExpanded = !_faqList[index].isExpanded
        )
    }
}

