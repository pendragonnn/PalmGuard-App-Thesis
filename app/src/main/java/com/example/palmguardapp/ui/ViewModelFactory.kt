package com.example.palmguardapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.palmguardapp.data.repository.DiseaseRepository
import com.example.palmguardapp.data.repository.HistoryDiagnoseRepository
import com.example.palmguardapp.di.Injection
import com.example.palmguardapp.ui.detection.DetectionViewModel
import com.example.palmguardapp.ui.home.HomeViewModel
import com.example.palmguardapp.ui.listDisease.DiseaseViewModel

class ViewModelFactory private constructor(
    private val historyDiagnoseRepository: HistoryDiagnoseRepository,
    private val diseaseRepository: DiseaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(historyDiagnoseRepository, diseaseRepository) as T
            }
            modelClass.isAssignableFrom(DetectionViewModel::class.java) -> {
                DetectionViewModel(historyDiagnoseRepository) as T
            }
            modelClass.isAssignableFrom(DiseaseViewModel::class.java) -> {
                DiseaseViewModel(diseaseRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val historyDiagnoseRepository = Injection.provideDiagnoseRepository(context)
                val diseaseRepository = Injection.provideDiseaseRepository(context)
                ViewModelFactory(historyDiagnoseRepository, diseaseRepository).also {
                    INSTANCE = it
                }
            }
        }
    }
}
