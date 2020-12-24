package me.gmur.buddywatch.recommendation.domain.app

import me.gmur.buddywatch.recommendation.domain.model.taste.command.SetFavoriteActorsCommand
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.springframework.stereotype.Service

@Service
class SetFavoriteActorsUseCase(private val tasteRepository: TasteRepository) {

    fun execute(command: SetFavoriteActorsCommand) {
        val token = command.token
        val actors = command.toActorsTaste()

        tasteRepository.store(actors, token)
    }
}
