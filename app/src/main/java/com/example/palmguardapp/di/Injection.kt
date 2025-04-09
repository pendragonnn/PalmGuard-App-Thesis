package com.example.palmguardapp.di

import android.content.Context
import com.example.palmguardapp.data.local.room.DiseaseDatabase
import com.example.palmguardapp.data.local.room.HistoryDatabase
import com.example.palmguardapp.data.repository.DiseaseRepository
import com.example.palmguardapp.data.repository.HistoryDiagnoseRepository

object Injection {
    fun provideDiagnoseRepository(context : Context) : HistoryDiagnoseRepository {
        val database = HistoryDatabase.getDatabase(context)
        val dao = database.historyDao()

        return HistoryDiagnoseRepository.getInstance(dao)
    }

    fun provideDiseaseRepository(context: Context): DiseaseRepository {
        val database = DiseaseDatabase.getDatabase(context)
        val dao = database.diseaseDiagnoseDao()

        return DiseaseRepository.getInstance(dao)
    }
}