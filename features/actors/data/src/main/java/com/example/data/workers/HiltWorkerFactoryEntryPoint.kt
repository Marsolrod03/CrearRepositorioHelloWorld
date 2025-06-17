package com.example.data.workers

import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HiltWorkerFactoryEntryPoint {
    fun workerFactory(): HiltWorkerFactory
}

//@Module
//@InstallIn(SingletonComponent::class)
//object WorkerModule {
//
//    @Provides
//    fun provideWorkerFactory(
//        hiltWorkerFactory: HiltWorkerFactory
//    ): WorkerFactory = hiltWorkerFactory
//}