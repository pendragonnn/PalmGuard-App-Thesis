package com.example.palmguardapp.data.repository

import com.example.palmguardapp.data.local.entity.DiseaseDiagnose
import com.example.palmguardapp.data.local.room.DiseaseDiagnoseDao
import kotlinx.coroutines.flow.Flow

//class DiseaseRepository private constructor(
//    private val apiService: ApiService
//) {
//
//    suspend fun getDiseaseById(id: String): Flow<Result<DiseaseByIdResponse>> = flow {
//        emit(Result.Loading)
//        val response = apiService.getDiseaseById(id)
//        emit(Result.Success(response))
//    }.catch { e ->
//        emit(Result.Error(e.message.toString()))
//    }
//
//    suspend fun getAllDiseaseDetail(): Flow<Result<DiseaseDetailResponse>> = flow {
//        emit(Result.Loading)
//        val response = apiService.getAllDiseaseDetail()
//        emit(Result.Success(response))
//    }.catch { e ->
//        emit(Result.Error(e.message.toString()))
//    }
//
//    suspend fun getDiseaseDetailById(id: String): Flow<Result<DiseaseDetailByIdResponse>> = flow {
//        emit(Result.Loading)
//        val response = apiService.getDiseaseDetailById(id)
//        emit(Result.Success(response))
//    }.catch { e ->
//        emit(Result.Error(e.message.toString()))
//    }
//
//    companion object {
//        @Volatile
//        private var instance: DiseaseRepository? = null
//
//        fun getInstance(apiService: ApiService): DiseaseRepository =
//            instance ?: synchronized(this) {
//                instance ?: DiseaseRepository(apiService).also { instance = it }
//            }
//    }
//}

class DiseaseRepository private constructor(
    private val diseaseDiagnoseDao: DiseaseDiagnoseDao
) {
    suspend fun insert(diseaseDiagnose: DiseaseDiagnose) {
        diseaseDiagnoseDao.insert(diseaseDiagnose)
    }

    suspend fun update(diseaseDiagnose: DiseaseDiagnose) {
        diseaseDiagnoseDao.update(diseaseDiagnose)
    }

    suspend fun delete(diseaseDiagnose: DiseaseDiagnose) {
        diseaseDiagnoseDao.delete(diseaseDiagnose)
    }

    fun getAllDisease(): Flow<List<DiseaseDiagnose>> = diseaseDiagnoseDao.getAllDisease()

    fun getDiseaseById(id: String): Flow<DiseaseDiagnose> = diseaseDiagnoseDao.getDiseaseById(id)


    companion object {
        @Volatile
        private var instance: DiseaseRepository? = null

        fun getInstance(diseaseDiagnoseDao: DiseaseDiagnoseDao): DiseaseRepository =
            instance ?: synchronized(this) {
                instance ?: DiseaseRepository(diseaseDiagnoseDao).also { instance = it }
            }
    }
}