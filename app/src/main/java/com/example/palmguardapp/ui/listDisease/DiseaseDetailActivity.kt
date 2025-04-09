package com.example.palmguardapp.ui.listDisease

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.palmguardapp.BuildConfig
import com.example.palmguardapp.R
import com.example.palmguardapp.data.local.entity.DiseaseDiagnose
import com.example.palmguardapp.databinding.ActivityDiseaseDetailBinding
import com.example.palmguardapp.foundation.utils.Result
import com.example.palmguardapp.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DiseaseDetailActivity : AppCompatActivity() {
    private val viewModel: DiseaseViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityDiseaseDetailBinding
    private var diseaseName: String? = null
    private val diseaseToId = mapOf(
        "Brown Spots" to "D-001",
        "Healthy" to "D-002",
        "Brown Spots" to "D-003",
        "Healthy" to "D-004",
        "Brown Spots" to "D-005",
        "Healthy" to "D-006",
        "Brown Spots" to "D-007",
        "Healthy" to "D-008"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDiseaseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.dgsBack.setOnClickListener {
            onBackPressed()
        }
        val bundle = intent.extras
        Log.d("bundleObserve", bundle?.getString("id").toString())
        diseaseName = bundle?.getString("diseaseName")

        observeViewModel()
    }

    private fun setUpUi(diseaseDetail: DiseaseDiagnose?) {
        if(diseaseDetail?.diseaseName == "Brown Spots") {
            Glide.with(this)
                .load(R.drawable.dgs_wereng_img)
                .into(binding.imageDisease)
        } else {
            Glide.with(this)
                .load(R.drawable.upn_logo)
                .into(binding.imageDisease)
        }

        binding.tvDescDisease.text = "Penjelasan Penyakit"
        binding.tvTitlePrevention.text = "Pencegahan"
        binding.tvTitleRecommended.text = "Rekomendasi Penanganan"
        binding.titleDisease.text = diseaseDetail?.diseaseName
        binding.descDisease.text = diseaseDetail?.detailExplanation
        binding.descPrevention.text = diseaseDetail?.detailPrevention
        binding.descRecommended.text = diseaseDetail?.detailRecommendation
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            diseaseName?.let { diseaseToId[it]?.let { it1 -> viewModel.getDiseaseById(it1) } }
            viewModel.diseaseById.collect { result ->
                when (result) {
                    is Result.Success -> {
                        binding.progressResult.visibility = android.view.View.GONE
                        setUpUi(result.data)
                    }
                    is Result.Error -> {
                        binding.progressResult.visibility = android.view.View.GONE
                        Snackbar.make(
                            binding.root,
                            "Error loading data: ${result.error}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is Result.Loading -> {
                        binding.progressResult.visibility = android.view.View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
