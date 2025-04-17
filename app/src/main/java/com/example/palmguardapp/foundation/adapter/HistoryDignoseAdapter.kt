package com.example.palmguardapp.foundation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.palmguardapp.data.local.entity.HistoryDiagnose
import com.example.palmguardapp.databinding.ItemResultBinding

class HistoryDiagnoseAdapter : ListAdapter<HistoryDiagnose, HistoryDiagnoseAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryDiagnose>() {
            override fun areItemsTheSame(oldItem: HistoryDiagnose, newItem: HistoryDiagnose): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HistoryDiagnose, newItem: HistoryDiagnose): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(history)
        }
    }

    inner class ViewHolder(private val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historyDiagnose: HistoryDiagnose) {
            binding.apply {
                binding.imgResultDiagnosis.setImageURI(Uri.parse(historyDiagnose.imageUri))
                titleResultDiagnosis.text = historyDiagnose.name
                val date = historyDiagnose.date.replace("-", " ")
                dateResultDiagnosis.text = date
                val confidenceFloat = historyDiagnose.confidence.toFloatOrNull() ?: 0f
                binding.confidenceResultDiagnosis.text = "Confidence: %.1f%%".format(confidenceFloat)
                delete.setOnClickListener {
                    onItemClickCallback.onDeleteClicked(historyDiagnose)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: HistoryDiagnose)
        fun onDeleteClicked(data: HistoryDiagnose)
    }

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }
}
