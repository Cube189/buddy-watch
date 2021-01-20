package me.gmur.buddywatch.vote.domain.model

class Ranking(_votes: List<MovieVote>) {

    val votes: List<MovieVote> = _votes.sortedByDescending { it.voteCount }
}
