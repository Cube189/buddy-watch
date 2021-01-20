package me.gmur.buddywatch.vote.adapter.persistence

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.GroupId
import me.gmur.buddywatch.jooq.tables.records.VoteRecord
import me.gmur.buddywatch.jooq.tables.references.LAST_PROVIDER_CACHE_FETCH_TIMESTAMP
import me.gmur.buddywatch.jooq.tables.references.VOTE
import me.gmur.buddywatch.recommendation.domain.model.MovieId
import me.gmur.buddywatch.vote.domain.model.Vote
import me.gmur.buddywatch.vote.domain.model.VoteId
import me.gmur.buddywatch.vote.domain.port.VoteRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import me.gmur.buddywatch.vote.adapter.persistence.VoteMapper as mapper

@Repository
class PostgresVoteRepository(private val db: DSLContext) : VoteRepository {

    override fun store(vote: Vote) {
        val lastCacheTimestamp = lastCacheTimestamp()

        val record = mapper.mapToRecord(vote, lastCacheTimestamp, db.newRecord(VOTE))

        record.store()
    }

    override fun allFor(groupId: GroupId): List<Vote> {
        val lastCacheTimestamp = lastCacheTimestamp()

        val records = db.selectFrom(VOTE)
            .where(VOTE.CACHE_FETCH_TIMESTAMP.eq(lastCacheTimestamp))
            .fetch()

        return records.map { mapper.mapToDomain(it) }
    }


    private fun lastCacheTimestamp(): LocalDateTime {
        return db.selectDistinct(LAST_PROVIDER_CACHE_FETCH_TIMESTAMP.FETCHED_ON)
            .from(LAST_PROVIDER_CACHE_FETCH_TIMESTAMP)
            .limit(1)
            .fetchOne()!!
            .into(LocalDateTime::class.java)
    }
}

private object VoteMapper {

    fun mapToDomain(record: VoteRecord): Vote {
        return Vote(
            id = VoteId.Persisted(record.id!!),
            token = Token(record.tokenId!!),
            groupId = GroupId.Persisted(record.groupId!!),
            movieId = MovieId.Persisted(record.movieId!!),
            liked = record.liked!!,
        )
    }

    fun mapToRecord(vote: Vote, lastCacheTimestamp: LocalDateTime, base: VoteRecord): VoteRecord {
        return base.apply {
            tokenId = vote.token.id.value
            groupId = vote.groupId.value
            movieId = vote.movieId.value
            cacheFetchTimestamp = lastCacheTimestamp
            liked = vote.liked
        }
    }
}
