package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.provider.domain.port.ProviderRepository
import me.gmur.buddywatch.recommendation.domain.model.taste.Genre
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteDecadesCommand
import me.gmur.buddywatch.recommendation.domain.port.GenreRepository
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.springframework.stereotype.Service

@Service
class SetFavoriteDecadesUseCase(
    private val tasteRepository: TasteRepository,
    private val groupRepository: GroupRepository,
    private val providerRepository: ProviderRepository,
    private val genreRepository: GenreRepository,
) {

    fun execute(command: SetFavoriteDecadesCommand): Set<Genre> {
        val token = command.token
        val region = command.region
        val decades = command.toDecadesTaste()

        tasteRepository.store(decades, token)

        val group = groupRepository.ofMember(token)
        val providers = providerRepository.findAll(group.providerShortnames)

        return genreRepository.fetchFor(decades, providers, region)
    }
}
