package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.group.domain.port.TokenRepository
import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteDecadesCommand
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.springframework.stereotype.Service

@Service
class SetFavoriteDecadesUseCase(private val tasteRepository: TasteRepository) {

    fun execute(command: SetFavoriteDecadesCommand) {
        val token = command.token
        val tastes = command.toDecadesTaste()

        tasteRepository.store(tastes, token)

        // TODO: Fetch movies from the decades
    }
}
