package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteDecadesCommand
import me.gmur.buddywatch.recommendation.domain.port.GenreClient
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.springframework.stereotype.Service
import me.gmur.buddywatch.justwatch.api.JwGenre

@Service
class SetFavoriteDecadesUseCase(
    private val tasteRepository: TasteRepository,
    private val groupRepository: GroupRepository,
    private val genreClient: GenreClient,
) {

    fun execute(command: SetFavoriteDecadesCommand): Set<JwGenre> {
        val token = command.token
        val region = command.region
        val decades = command.toDecadesTaste()

        tasteRepository.store(decades, token)

        val group = groupRepository.ofMember(token)

        return genreClient.fetchFor(decades, group, region)
    }
}
