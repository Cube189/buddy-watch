package me.gmur.buddywatch.recommendation.domain.model.taste

class DirectorsTaste(val directors: Set<Director>)

data class Director(val name: String, val reference: Long)
