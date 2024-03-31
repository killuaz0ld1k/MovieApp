package com.example.cinema.data.work_manager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cinema.data.local.LocalDataSource
import com.example.cinema.data.remote.RemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class LoadMovieDetailsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CoroutineWorker(context,params) {
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                localDataSource.insertMovieDetails(remoteDataSource.loadMovie(1125311))
            }
            Log.i("movie","success")
            Result.success()
        }
        catch (e : Exception) {
            Log.e("workerError", e.toString())
            Result.failure()
        }
    }
}