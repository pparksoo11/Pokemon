package com.soo.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soo.data.common.ApiResult
import com.soo.data.mapper.toDomain
import com.soo.data.remote.datasource.PokemonDataSource
import com.soo.domain.model.Pokemon

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
            when(val result = pokemonDataSource.getPokemonList(limit, offset)) {
                is ApiResult.Success -> {
                    val response = result.body
                    val isEnd = (offset + limit) >= (response.count ?: 0)

                    LoadResult.Page(
                        data = response.toDomain(),
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (isEnd) null else page + 1
                    )
                }

                is ApiResult.GenericError -> {
                    LoadResult.Error(Throwable("API 오류 [${result.code}]: ${result.errorInfo}"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}