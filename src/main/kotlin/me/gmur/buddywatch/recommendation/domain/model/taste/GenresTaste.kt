package me.gmur.buddywatch.recommendation.domain.model.taste

class GenresTaste(val genres: Set<Genre>)

data class Genre(val name: String, val shorthand: String, val reference: Int)
