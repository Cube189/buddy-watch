package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.recommendation.domain.model.taste.Actor
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteDirectorsCommand
import me.gmur.buddywatch.recommendation.domain.port.ActorRepository
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.springframework.stereotype.Service

@Service
class SetFavoriteDirectorsUseCase(
    private val tasteRepository: TasteRepository,
    private val groupRepository: GroupRepository,
    private val actorRepository: ActorRepository,
) {

    fun execute(command: SetFavoriteDirectorsCommand): Set<Actor> {
        val token = command.token
        val directors = command.toDirectorsTaste()
        val decades = tasteRepository.getDecades(token)
        val genres = tasteRepository.getGenres(token)

        tasteRepository.store(directors, token)

        val group = groupRepository.ofMember(token)

        return actorRepository.fetchFor(decades, genres, group)
    }
}
