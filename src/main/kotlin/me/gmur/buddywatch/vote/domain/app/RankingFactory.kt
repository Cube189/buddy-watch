package me.gmur.buddywatch.vote.domain.app

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.GroupId
import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.recommendation.domain.model.MovieId
import me.gmur.buddywatch.recommendation.domain.port.MovieRepository
import me.gmur.buddywatch.vote.domain.model.MovieVote
import me.gmur.buddywatch.vote.domain.model.Ranking
import me.gmur.buddywatch.vote.domain.model.Vote
import me.gmur.buddywatch.vote.domain.port.VoteRepository
import org.springframework.stereotype.Service

@Service
class RankingFactory(
    private val voteRepository: VoteRepository,
    private val movieRepository: MovieRepository,
    private val groupRepository: GroupRepository,
) {

    fun create(token: Token): Ranking {
        val groupId = groupRepository.ofMember(token).id

        val votes = voteRepository.allFor(groupId)

        val groupedMovies = votes.groupBy { it.movieId }
        val movieVotes = groupedMovies.map { mapToMovieVote(it) }

        return Ranking(movieVotes)
    }

    private fun mapToMovieVote(entry: Map.Entry<MovieId, List<Vote>>): MovieVote {
        val movieId = entry.key
        val votes = entry.value

        val movie = movieRepository.find(movieId)
        val likes = votes.filter { it.liked }

        return MovieVote(movie, likes.size)
    }
}
