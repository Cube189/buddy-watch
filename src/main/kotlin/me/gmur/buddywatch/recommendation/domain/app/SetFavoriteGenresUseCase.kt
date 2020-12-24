package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteGenresCommand
import me.gmur.buddywatch.recommendation.domain.port.ActorClient
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.springframework.stereotype.Service
import me.gmur.buddywatch.recommendation.domain.model.taste.Actor

@Service
class SetFavoriteGenresUseCase(
    private val tasteRepository: TasteRepository,
    private val groupRepository: GroupRepository,
    private val actorClient: ActorClient,
) {

    fun execute(command: SetFavoriteGenresCommand): Set<Actor> {
        val token = command.token
        val genres = command.toGenresTaste()
        val decades = tasteRepository.getDecades(token)

        tasteRepository.store(genres, token)

        val group = groupRepository.ofMember(token)

        return actorClient.fetchFor(decades, genres, group)
    }
}
