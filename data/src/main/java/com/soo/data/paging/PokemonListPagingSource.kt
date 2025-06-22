package com.soo.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soo.data.mapper.toDomain
import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.domain.model.Pokemon
import java.io.IOException

class PokemonListPagingSource(
    private val pokemonDataSource: PokemonDataSource
): PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val page = params.key ?: 0
        val limit = 20
        val offset = page * limit

        return try {
            val result = pokemonDataSource.getPokemonList(limit, offset)
            val isEnd = (offset + limit) >= (result.count ?: 0)

            LoadResult.Page(
                data = result.toDomain(),
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (isEnd) null else page + 1
            )
        } catch (e: IOException) { // 네트워크 오류
            Log.e("PokemonListPagingSource", "Network error: ${e.message}")
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e("PokemonListPagingSource", "Error loading data: ${e.message}")
            LoadResult.Error(e)
        }
    }
}