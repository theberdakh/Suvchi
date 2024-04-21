package com.theberdakh.suvchi.ui.statistics

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.theberdakh.suvchi.data.remote.UserApi
import com.theberdakh.suvchi.data.remote.model.statistics.DayUsageStatistics

class StatisticsPagingSource(
    private val api: UserApi,
    private val from: String,
    private val to: String
): PagingSource<Int, DayUsageStatistics>() {
    override fun getRefreshKey(state: PagingState<Int, DayUsageStatistics>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DayUsageStatistics> {
        val position = params.key ?: 1
        val response = api.getConsumptionStatistics(from, to)


        return try {
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = if (position == response.body()!!.count()) null else (position + 1)
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