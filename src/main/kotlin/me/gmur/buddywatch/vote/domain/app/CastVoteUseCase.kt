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

    fun execute(command: CastVoteCommand): Int {
        val group = groupRepository.ofMember(command.token)

        val votesCast = voteRepository.allFor(group.id).size
        val maxVotes = group.votesPerMember

        if (votesCast > maxVotes) throw NumberOfCastVotesExceeded()

        val vote = Vote(
            token = command.token,
            groupId = group.id,
            movieId = command.movieId,
            liked = command.liked
        )

        voteRepository.store(vote)

        return maxVotes - votesCast
    }
}
