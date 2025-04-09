package com.example.palmguardapp.ui.listDisease

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.palmguardapp.data.local.entity.DiseaseDiagnose
import com.example.palmguardapp.data.repository.DiseaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import com.example.palmguardapp.foundation.utils.Result
import kotlinx.coroutines.flow.asSharedFlow

class DiseaseViewModel(private val diseaseRepository: DiseaseRepository) : ViewModel() {

    private val _listDisease = MutableSharedFlow<Result<List<DiseaseDiagnose>>>()
    val listDisease: Flow<Result<List<DiseaseDiagnose>>> = _listDisease.asSharedFlow()

    private val _diseaseById = MutableSharedFlow<Result<DiseaseDiagnose?>>()
    val diseaseById: Flow<Result<DiseaseDiagnose?>> = _diseaseById.asSharedFlow()

//    fun getAllDisease() {
//        viewModelScope.launch {
//            _listDisease.emit(Result.Loading)
//            diseaseRepository.getAllDisease().collect { diseases ->
//                _listDisease.emit(Result.Success(diseases))
//            }
//        }
//    }

    fun getDiseaseById(id: String) {
        viewModelScope.launch {
            _diseaseById.emit(Result.Loading)
            diseaseRepository.getDiseaseById(id).collect { disease ->
                if (disease != null) {
                    _diseaseById.emit(Result.Success(disease))
                } else {
                    _diseaseById.emit(Result.Error("Penyakit tidak ditemukan di database lokal"))
                }
            }
        }
    }
}