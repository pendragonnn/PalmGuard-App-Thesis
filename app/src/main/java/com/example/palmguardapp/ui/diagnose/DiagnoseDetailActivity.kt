package com.example.palmguardapp.ui.diagnose

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.palmguardapp.R
import com.example.palmguardapp.data.local.entity.HistoryDiagnose
import com.example.palmguardapp.databinding.ActivityDiagnoseBinding
import com.example.palmguardapp.foundation.adapter.VPAdapter
import com.example.palmguardapp.ui.listDisease.DiseaseDetailActivity
import com.google.android.material.tabs.TabLayoutMediator

class DiagnoseDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnoseBinding
    private lateinit var historyDiagnose: HistoryDiagnose
    private lateinit var returnFragment : String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diagnose)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityDiagnoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyDiagnose = intent.getParcelableExtra("HISTORY_DIAGNOSE")!!
        returnFragment = intent.getStringExtra("RETURN_FRAGMENT")!!

        binding.tvTitle.text = historyDiagnose.name
        binding.ivImgTea.setImageURI(Uri.parse(historyDiagnose.imageUri))
        val confidenceFloat = historyDiagnose.confidence.toFloatOrNull() ?: 0f
        binding.tvConfidence.text = "Confidence: %.1f%%".format(confidenceFloat)
        binding.viewPager.adapter = VPAdapter(this, historyDiagnose)
        binding.btnHsGallery.setOnClickListener {
            val bundle = Bundle().apply {
                putString("diseaseName", historyDiagnose.name)
            }
            val intent = Intent(this, DiseaseDetailActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Diagnosa"
                1 -> tab.text = "Rekomendasi"
            }
        }.attach()

        binding.dgsBack.setOnClickListener {
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
