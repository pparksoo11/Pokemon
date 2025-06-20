package com.soo.presentation.fragment

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.soo.presentation.R
import com.soo.presentation.adapter.PokemonListAdapter
import com.soo.presentation.adapter.PokemonLoadStateAdapter
import com.soo.presentation.base.BaseFragment
import com.soo.presentation.databinding.FragmentListBinding
import com.soo.presentation.viewmodel.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val pokemonListViewModel by viewModels<PokemonListViewModel>()

    private lateinit var pokemonListAdapter: PokemonListAdapter
    private lateinit var pokemonLoadStateAdapter: PokemonLoadStateAdapter

    override fun initView() {
        setupPokemonList()
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonListViewModel.pokemonList.collectLatest { pagingData ->
                    pokemonListAdapter.submitData(pagingData)
                }
            }
        }
    }

    /**
     * pokemon 리스트 셋팅
     * */
    private fun setupPokemonList() {
        pokemonListAdapter = PokemonListAdapter()
        pokemonLoadStateAdapter = PokemonLoadStateAdapter()

        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
                binding.pokemonList.layoutManager = gridLayoutManager

        binding.pokemonList.adapter = pokemonListAdapter

        pokemonListAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.source.refresh is LoadState.NotLoading && pokemonListAdapter.itemCount == 0
            val isLoading = loadState.source.refresh is LoadState.Loading
            val hasError = loadState.source.refresh is LoadState.Error || loadState.source.append is LoadState.Error || loadState.source.prepend is LoadState.Error

            binding.progressBar.isVisible = isLoading
            binding.pokemonList.isVisible = !isLoading && !isListEmpty
            binding.tvError.isVisible = hasError || isListEmpty
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 초기 데이터 로딩
                pokemonListViewModel.getPokemonList()
            }
        }
    }
}