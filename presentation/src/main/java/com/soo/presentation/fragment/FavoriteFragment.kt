package com.soo.presentation.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.soo.presentation.R
import com.soo.presentation.adapter.FavoritePokemonAdapter
import com.soo.presentation.base.BaseFragment
import com.soo.presentation.databinding.FragmentListBinding
import com.soo.presentation.viewmodel.PokemonFavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private lateinit var favoritePokemonListAdapter: FavoritePokemonAdapter

    private val pokemonFavoriteViewModel by viewModels<PokemonFavoriteViewModel>()

    override fun initView() {
        requireActivity().title = "Favorite"
        setupFavoriteList()
    }

    override fun observeViewModel() {
        super.observeViewModel()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonFavoriteViewModel.favoritePokemonList.collectLatest { list ->
                    favoritePokemonListAdapter.submitList(list)
                }
            }
        }
    }

    // 즐겨찾기 리스트를 설정하는 로직을 여기에 추가합니다.
    private fun setupFavoriteList() {
        favoritePokemonListAdapter = FavoritePokemonAdapter()

        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.pokemonList.layoutManager = gridLayoutManager
        binding.pokemonList.adapter = favoritePokemonListAdapter

        pokemonFavoriteViewModel.getFavoritePokemonList()
    }
}