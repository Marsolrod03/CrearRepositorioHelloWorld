//package com.example.crearrepositorio.features.series.data
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.crearrepositorio.features.series.domain.SerieModel
//
//class SeriesPagingSource(
//    private val service: SeriesNetworkDataSource
//) : PagingSource<Int, SerieModel>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SerieModel> {
//        val page = params.key ?: 1
//        return try {
//            val response = service.fetchSeries(page)
//            val series = response.map{it.toSeriesModel()}
//
//            LoadResult.Page(
//                data = series,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (series.isEmpty()) null else page + 1
//            )
//        }catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, SerieModel>): Int? {
//        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey }
//    }
//}