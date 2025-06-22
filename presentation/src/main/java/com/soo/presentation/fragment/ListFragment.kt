package com.soo.presentation.fragment

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
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
import java.io.IOException

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val pokemonListViewModel by viewModels<PokemonListViewModel>()

    private lateinit var pokemonListAdapter: PokemonListAdapter
    private lateinit var pokemonLoadStateAdapter: PokemonLoadStateAdapter

    override fun initView() {
        requireActivity().title = "List"
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
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error

            val hasError = errorState != null

            binding.progressBar.isVisible = isLoading
            binding.pokemonList.isVisible = !isLoading && !isListEmpty && !hasError

            binding.tvError.apply {
                isVisible = isListEmpty || hasError
                text = when {
                    hasError -> when (val error = errorState?.error) {
                        is IOException -> "네트워크 연결을 확인해주세요."
                        else -> "알 수 없는 오류가 발생했습니다."
                    }
                    isListEmpty -> "표시할 포켓몬이 없습니다."
                    else -> ""
                }
            }

            /*binding.btnRetry.apply {
                isVisible = hasError
                setOnClickListener { pokemonListAdapter.retry() }
            }*/
        }

        // 초기 데이터 로딩
        pokemonListViewModel.getPokemonList()
    }
}