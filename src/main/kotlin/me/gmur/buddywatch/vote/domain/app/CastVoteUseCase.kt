package me.gmur.buddywatch.vote.domain.app

import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.vote.domain.model.Vote
import me.gmur.buddywatch.vote.domain.model.command.CastVoteCommand
import me.gmur.buddywatch.vote.domain.port.VoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CastVoteUseCase(
    private val groupRepository: GroupRepository,
    private val voteRepository: VoteRepository,
) {

    fun execute(command: CastVoteCommand) {
        val group = groupRepository.ofMember(command.token)
        
        val vote = Vote(
            token = command.token,
            groupId = group.id,
            movieId = command.movieId,
            liked = command.liked
        )

        voteRepository.store(vote)
    }
}
