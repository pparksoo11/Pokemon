package com.soo.presentation.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.soo.presentation.databinding.ItemProgressBinding

class LoadStateViewHolder(private val binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }
}