package me.gmur.buddywatch.recommendation.domain.model.taste

class GenresTaste(val genres: Set<Genre>)

data class Genre(val value: String, val shorthand: String)
