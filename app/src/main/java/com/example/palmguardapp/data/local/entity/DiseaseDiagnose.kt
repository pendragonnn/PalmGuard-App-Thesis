package com.example.palmguardapp.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DiseaseDiagnose(
    @PrimaryKey()
    val id: String = "",
    val diseaseName: String,
    val diseaseExplanation: String,
    val diseaseRecommendation: String,
    val detailExplanation: String,
    val detailPrevention: String,
    val detailRecommendation: String,
    val date: String
) : Parcelable