package com.theberdakh.suvchi.ui.contracts

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.theberdakh.suvchi.data.remote.UserApi
import com.theberdakh.suvchi.data.remote.model.contract.AllContractsEntity

class ContractsPagingSource (
    val api: UserApi
): PagingSource<Int, AllContractsEntity>(){
    override fun getRefreshKey(state: PagingState<Int, AllContractsEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllContractsEntity> {
        val position = params.key ?: 1
        val response = api.getUserContracts(1, 1)


      return try {
          if (response.isSuccessful) {
              LoadResult.Page(
                  data = response.body()!!.data,
                  prevKey = if (position == 1) null else (position - 1),
                  nextKey = if (position == response.body()!!.count) null else (position + 1)
              )
          } else {
              Log.i("Paging Source", "load: No Response")
              LoadResult.Error(throw Exception("No Response"))
          }
      } catch (e: Exception){
          Log.i("TAG", "load: No Response")
          LoadResult.Error(throw Exception("No response"))
      }

    }
}