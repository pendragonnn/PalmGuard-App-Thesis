package com.example.palmguardapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.palmguardapp.data.local.entity.DiseaseDiagnose
import com.example.palmguardapp.data.local.entity.HistoryDiagnose
import com.example.palmguardapp.data.repository.DiseaseRepository
import com.example.palmguardapp.data.repository.HistoryDiagnoseRepository
import com.example.palmguardapp.foundation.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel (
    private val historyDiagnoseRepository: HistoryDiagnoseRepository,
    private val diseaseRepository: DiseaseRepository
) : ViewModel() {

    private val _dataDisease = MutableSharedFlow<Result<DiseaseDiagnose>>()
    val dataDisease : Flow<Result<DiseaseDiagnose>> = _dataDisease.asSharedFlow()

    private val _dataLastResult = MutableSharedFlow<HistoryDiagnose>()
    val dataLastResult : Flow<HistoryDiagnose> = _dataLastResult.asSharedFlow()

    init {
        getLastHistory()
    }
    fun getDiseaseById(id: String) {
        viewModelScope.launch {
            diseaseRepository.getDiseaseById(id).collect{ disease ->
                _dataDisease.emit(Result.Success(disease))
            }
        }
    }
    suspend fun saveDiagnose(historyDiagnose: HistoryDiagnose) {
        viewModelScope.launch {
            historyDiagnoseRepository.insert(historyDiagnose)
        }
    }
    private fun getLastHistory() {
        viewModelScope.launch {
            historyDiagnoseRepository.getLastHistory().collect{
                _dataLastResult.emit(it)
            }
        }
    }
}