package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.recommendation.domain.model.taste.Actor
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteGenresCommand
import me.gmur.buddywatch.recommendation.domain.port.DirectorClient
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.springframework.stereotype.Service

@Service
class SetFavoriteGenresUseCase(
    private val tasteRepository: TasteRepository,
    private val groupRepository: GroupRepository,
    private val directorClient: DirectorClient,
) {

    fun execute(command: SetFavoriteGenresCommand): Set<Director> {
        val token = command.token
        val genres = command.toGenresTaste()
        val decades = tasteRepository.getDecades(token)

        tasteRepository.store(genres, token)

        val group = groupRepository.ofMember(token)

        return directorClient.fetchFor(decades, genres, group)
    }
}
