package com.example.data.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.use_case.MoviesDeleteDatabaseUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class ResetDatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val deleteDatabaseUseCase: MoviesDeleteDatabaseUseCase) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
       return@withContext try {
            deleteDatabaseUseCase()
           Log.e("bd_deleted", "---BASE DE DATOS BORRADA---")
            Result.success()
        }catch (e: Exception){
            e.printStackTrace()
           Log.e("bd_not_deleted", "---ERROR AL BORRAR BD---")
            Result.failure()
        }
    }

}