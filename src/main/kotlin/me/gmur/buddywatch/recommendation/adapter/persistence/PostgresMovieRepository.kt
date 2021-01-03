package me.gmur.buddywatch.recommendation.adapter.persistence

import me.gmur.buddywatch.jooq.tables.records.CastMemberRecord
import me.gmur.buddywatch.jooq.tables.records.MovieRecord
import me.gmur.buddywatch.jooq.tables.references.CAST_MEMBER
import me.gmur.buddywatch.jooq.tables.references.MOVIE
import me.gmur.buddywatch.recommendation.domain.model.CastMember
import me.gmur.buddywatch.recommendation.domain.model.CastMemberId
import me.gmur.buddywatch.recommendation.domain.model.FetchedMovie
import me.gmur.buddywatch.recommendation.domain.model.Movie
import me.gmur.buddywatch.recommendation.domain.model.MovieId
import me.gmur.buddywatch.recommendation.domain.port.MovieRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class PostgresMovieRepository(private val db: DSLContext) : MovieRepository {

    override fun store(fetchedMovies: List<FetchedMovie>, timestamp: LocalDateTime) {
        val cast = fetchedMovies.map { it.cast }.map { store(it, timestamp) }
        val actorAndDirectorIds = cast.map { splitIntoActorAndDirectorIds(it) }

        val moviesWithActorIdsAndDirectorIds = fetchedMovies zip actorAndDirectorIds

        val records = moviesWithActorIdsAndDirectorIds.map {
            FetchedMovieMapper.mapToRecord(it.first, it.second.first, it.second.second, db.newRecord(MOVIE))
        }
        records.forEach { it.fetchedOn = timestamp }

        db.batchStore(records).execute()
    }

    private fun store(cast: List<CastMember>, timestamp: LocalDateTime): List<CastMemberRecord> {
        val records = cast.map { CastMemberMapper.mapToRecord(it, db.newRecord(CAST_MEMBER)) }

        records.forEach { it.fetchedOn = timestamp }
        records.forEach { it.store() }

        return records
    }

    private fun splitIntoActorAndDirectorIds(cast: List<CastMemberRecord>): Pair<List<Long>, List<Long>> {
        val actors = cast.filter { it.role == "ACTOR" }
        val actorIds = actors.map { it.id!! }

        val directors = cast.filter { it.role == "DIRECTOR" }
        val directorIds = directors.map { it.id!! }

        return Pair(actorIds, directorIds)
    }
}

private object MovieMapper {

    fun mapToDomain(source: MovieRecord): Movie {
        return Movie(
            id = MovieId.Persisted(source.id!!),
            title = source.title!!,
            description = source.description!!,
            released = source.released!!,
            actorIds = source.actorIds!!.mapNotNull { CastMemberId.Persisted(it!!) }.toList(),
            directorIds = source.directorIds!!.mapNotNull { CastMemberId.Persisted(it!!) }.toList(),
            genreReferences = source.genreIds!!.mapNotNull { it }.toSet(),
            reference = source.reference!!,
        )
    }
}

private object FetchedMovieMapper {

    fun mapToRecord(
        source: FetchedMovie,
        actorIds: List<Long>,
        directorIds: List<Long>,
        base: MovieRecord
    ): MovieRecord {
        return base.apply {
            this.title = source.title
            this.description = source.description
            this.released = source.released
            this.actorIds = actorIds.toTypedArray()
            this.directorIds = directorIds.toTypedArray()
            this.genreIds = source.genreReferences.toTypedArray()
            this.reference = source.reference
        }
    }
}

private object CastMemberMapper {

    fun mapToDomain(source: CastMemberRecord): CastMember {
        return CastMember(
            id = CastMemberId.Persisted(source.id!!),
            name = source.name!!,
            role = source.role!!,
            reference = source.reference!!
        )
    }

    fun mapToRecord(source: CastMember, base: CastMemberRecord): CastMemberRecord {
        return base.apply {
            name = source.name
            role = source.role
            reference = source.reference
        }
    }
}
