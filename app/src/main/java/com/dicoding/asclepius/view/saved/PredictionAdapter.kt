package com.dicoding.asclepius.view.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.databinding.ItemRowPredictionBinding
import com.dicoding.asclepius.data.local.entity.PredictionEntity


class PredictionAdapter(
    private val predictionViewModel: PredictionViewModel
) : ListAdapter<PredictionEntity, PredictionAdapter.PredictionViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val binding = ItemRowPredictionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PredictionViewHolder(binding, predictionViewModel)
    }

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PredictionViewHolder(
        private val binding: ItemRowPredictionBinding,
        private val predictionViewModel: PredictionViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PredictionEntity) {
            Glide.with(binding.resultImage.context)
                .load(item.image)
                .into(binding.resultImage)

            binding.resultCategory.text = item.category
            binding.resultScore.text = item.score
            binding.buttonDelete.setOnClickListener {
                predictionViewModel.removePrediction(item.id)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PredictionEntity> =
            object : DiffUtil.ItemCallback<PredictionEntity>() {
                override fun areItemsTheSame(oldItem: PredictionEntity, newItem: PredictionEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: PredictionEntity, newItem: PredictionEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
