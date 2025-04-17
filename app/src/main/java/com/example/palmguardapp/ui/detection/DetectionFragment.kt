package com.example.palmguardapp.ui.detection

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.palmguardapp.R
import com.example.palmguardapp.data.local.entity.HistoryDiagnose
import com.example.palmguardapp.databinding.FragmentDetectionBinding
import com.example.palmguardapp.foundation.adapter.HistoryDiagnoseAdapter
import com.example.palmguardapp.ui.ViewModelFactory
import com.example.palmguardapp.ui.diagnose.DiagnoseDetailActivity
import kotlinx.coroutines.launch

class DetectionFragment : Fragment(R.layout.fragment_detection) {

    private var _binding: FragmentDetectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetectionViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val detectionAdapter = HistoryDiagnoseAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeDetectionList()
    }

    private fun setupRecyclerView() {
        binding.rvResultDetection.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResultDetection.adapter = detectionAdapter

        detectionAdapter.setOnItemClickCallback(object : HistoryDiagnoseAdapter.OnItemClickCallback {
            override fun onItemClicked(data: HistoryDiagnose) {
                navigateToDiagnoseDetail(data)
            }

            fun onDeleteClicked(data: HistoryDiagnose) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Riwayat")
                    .setMessage("Apakah kamu yakin ingin menghapus riwayat ini?")
                    .setPositiveButton("Hapus") { _, _ ->
                        lifecycleScope.launch {
                            viewModel.deleteHistory(data)
                        }
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }

        })
    }

    private fun navigateToDiagnoseDetail(historyDiagnose: HistoryDiagnose) {
        val intent = Intent(requireActivity(), DiagnoseDetailActivity::class.java)
        intent.putExtra("HISTORY_DIAGNOSE", historyDiagnose)
        intent.putExtra("RETURN_FRAGMENT", "detection")
        startActivity(intent)
    }
    private fun observeDetectionList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detectionList.collect { historyDiagnoses ->
                Log.d("DetectionFragment", "Observing detection list. Count: ${historyDiagnoses.size}")
                detectionAdapter.submitList(historyDiagnoses)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
