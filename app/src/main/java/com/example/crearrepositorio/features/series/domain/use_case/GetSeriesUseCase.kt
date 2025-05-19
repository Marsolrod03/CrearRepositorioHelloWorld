package com.example.crearrepositorio.features.series.domain.use_case


import com.example.crearrepositorio.features.series.domain.repository.SeriesRepository
import com.example.crearrepositorio.features.series.domain.SeriesWrapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar

class GetSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    private var currentPage = 1
    private var databaseLoading = false

    operator fun invoke(): Flow<Result<SeriesWrapper>> = flow {
       if(seriesRepository.getAllSeriesFromDatabase().isNotEmpty() && !databaseLoading){
           databaseLoading = true
           emit(Result.success(SeriesWrapper(true, seriesRepository.getAllSeriesFromDatabase())))

       }else{
           if(seriesRepository.getPaginationSeries() >= 1){
               currentPage = seriesRepository.getPaginationSeries()
           }
           seriesRepository.getPagedSeriesFromApi(currentPage)
               .collect{
                   result ->
                   result.onSuccess {
                       seriesWrapper -> seriesRepository.insertSeries(seriesWrapper.listSeries)
                       currentPage++
                       if(currentPage > seriesRepository.getPaginationSeries()){
                           seriesRepository.updatePaginationSeries(currentPage)
                       }
                       emit(Result.success(seriesWrapper))
                   }
               }
       }
    }

    private suspend fun handleWeeklyReset() {
        if (isMonday8AM()) {
            seriesRepository.refreshData()
            currentPage = 1
        } else {
            currentPage = seriesRepository.getPaginationSeries()
        }
    }

    private fun isMonday8AM(): Boolean {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        return dayOfWeek == Calendar.MONDAY && hourOfDay == 8
    }
}
