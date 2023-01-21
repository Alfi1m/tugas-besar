package com.project.proyek_akhir_mobile_programming.core.data.repository

import com.project.proyek_akhir_mobile_programming.core.data.remote.MovieRemoteDataSource
import com.project.proyek_akhir_mobile_programming.core.data.remote.Resource
import com.project.proyek_akhir_mobile_programming.core.data.remote.network.ApiResponse
import com.project.proyek_akhir_mobile_programming.core.domain.model.Movie
import com.project.proyek_akhir_mobile_programming.core.domain.model.TvShow
import com.project.proyek_akhir_mobile_programming.core.domain.repository.IMovieRepository
import com.project.proyek_akhir_mobile_programming.core.utils.mapMovieResponseToDomain
import com.project.proyek_akhir_mobile_programming.core.utils.mapTvShowResponseToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class MovieRepository(private val movieRemoteDataSource: MovieRemoteDataSource): IMovieRepository {
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        flow {
            emit(Resource.Loading())
            when(val apiResponse = movieRemoteDataSource.getMovies().first()){
                is ApiResponse.Success -> emit(Resource.Success(apiResponse.data.map { mapMovieResponseToDomain(it) }))
                is ApiResponse.Empty -> emit(Resource.Success(null))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    override fun getTvShow(): Flow<Resource<List<TvShow>>> =
        flow {
            emit(Resource.Loading())
            when(val apiResponse = movieRemoteDataSource.getTvShow().first()){
                is ApiResponse.Success -> emit(Resource.Success(apiResponse.data.map { mapTvShowResponseToDomain(it)}))
                is ApiResponse.Empty -> emit(Resource.Success(null))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
            }
        }

}