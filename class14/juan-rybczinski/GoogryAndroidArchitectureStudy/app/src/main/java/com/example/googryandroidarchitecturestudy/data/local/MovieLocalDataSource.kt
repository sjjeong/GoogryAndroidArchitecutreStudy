package com.example.googryandroidarchitecturestudy.data.local

import com.example.googryandroidarchitecturestudy.database.DatabaseMovie

interface MovieLocalDataSource {
    suspend fun searchMovies(search: String): List<DatabaseMovie>

    suspend fun insertAll(movies: List<DatabaseMovie>)
}