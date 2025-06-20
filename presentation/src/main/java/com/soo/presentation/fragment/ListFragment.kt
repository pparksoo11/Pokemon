package com.soo.presentation.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.soo.presentation.R
import com.soo.presentation.adapter.PokemonListAdapter
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

    override fun initView() {
        pokemonListAdapter = PokemonListAdapter()

        binding.pokemonList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.pokemonList.adapter = pokemonListAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonListViewModel.getPokemonList()
            }
        }
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
}