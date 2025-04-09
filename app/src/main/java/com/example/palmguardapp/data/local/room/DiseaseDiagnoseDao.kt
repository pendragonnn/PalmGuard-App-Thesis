package com.example.palmguardapp.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.palmguardapp.data.local.entity.DiseaseDiagnose
import kotlinx.coroutines.flow.Flow

@Dao
interface DiseaseDiagnoseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(diseaseDiagnose: DiseaseDiagnose)

    @Update
    suspend fun update(diseaseDiagnose: DiseaseDiagnose)

    @Delete
    suspend fun delete(diseaseDiagnose: DiseaseDiagnose)

    @Query("SELECT * FROM diseasediagnose")
    fun getAllDisease(): Flow<List<DiseaseDiagnose>>

    @Query("SELECT * FROM diseasediagnose WHERE id = :id")
    fun getDiseaseById(id: String): Flow<DiseaseDiagnose>

}
