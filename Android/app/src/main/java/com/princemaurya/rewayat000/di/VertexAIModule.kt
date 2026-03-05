package com.princemaurya.rewayat000.di

import com.google.firebase.vertexai.FirebaseVertexAI
import com.google.firebase.vertexai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VertexAIModule {

    @Provides
    @Singleton
    fun provideVertexAI(): FirebaseVertexAI = FirebaseVertexAI.getInstance()

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        // Using gemini-1.5-flash for speed and cost-efficiency as a co-pilot
        return FirebaseVertexAI.getInstance().generativeModel("gemini-1.5-flash")
    }
}
