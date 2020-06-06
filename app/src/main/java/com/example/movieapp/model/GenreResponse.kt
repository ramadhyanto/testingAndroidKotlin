package com.example.movieapp.model

data class GenreResponse(
	val genres: List<GenresItem?>? = null
)

data class GenresItem(
	val name: String? = null,
	val id: Int? = null
)

