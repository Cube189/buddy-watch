package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.provider.domain.port.ProviderRepository
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteGenresCommand
import me.gmur.buddywatch.recommendation.domain.port.DirectorRepository
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.springframework.stereotype.Service

@Service
class SetFavoriteGenresUseCase(
    private val tasteRepository: TasteRepository,
    private val groupRepository: GroupRepository,
    private val providerRepository: ProviderRepository,
    private val directorRepository: DirectorRepository,
) {

    fun execute(command: SetFavoriteGenresCommand): Set<Director> {
        val token = command.token
        val genres = command.toGenresTaste()
        val decades = tasteRepository.getDecades(token)

        tasteRepository.store(genres, token)

        val group = groupRepository.ofMember(token)
        val providers = providerRepository.get(group.providerShortnames)

        return directorRepository.fetchFor(decades, genres, providers)
    }
}
