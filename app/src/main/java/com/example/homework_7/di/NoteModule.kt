package com.example.homework_7.di

import android.content.Context
import androidx.room.Room
import com.example.homework_7.data.local.NoteDao
import com.example.homework_7.data.local.NoteDatabase
import com.example.homework_7.data.repository.NoteRepositoryImpl
import com.example.homework_7.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): com.example.homework_7.data.local.NoteDatabase {
        return Room.databaseBuilder(
            context,
            com.example.homework_7.data.local.NoteDatabase::class.java,
            "note_db"
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: com.example.homework_7.data.local.NoteDatabase) = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideNoteRepository(noteDao: com.example.homework_7.data.local.NoteDao) : com.example.homework_7.domain.repository.NoteRepository =
        com.example.homework_7.data.repository.NoteRepositoryImpl(noteDao)

}