package me.gmur.buddywatch.recommendation.domain.model.taste.command

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.DirectorsTaste

class SetFavoriteDirectorsCommand(val directors: Set<Director>, val token: Token) {

    fun toDirectorsTaste(): DirectorsTaste {
        return DirectorsTaste(directors)
    }
}
