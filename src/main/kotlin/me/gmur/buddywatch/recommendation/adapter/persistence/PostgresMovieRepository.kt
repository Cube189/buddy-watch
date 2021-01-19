package me.gmur.buddywatch.recommendation.adapter.persistence

import me.gmur.buddywatch.jooq.tables.records.CastMemberRecord
import me.gmur.buddywatch.jooq.tables.records.MovieRecord
import me.gmur.buddywatch.jooq.tables.references.CAST_MEMBER
import me.gmur.buddywatch.jooq.tables.references.LAST_MOVIE_CACHE_FETCH_TIMESTAMP
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

    override fun store(fetchedMovies: List<FetchedMovie>) {
        val timestamp = LocalDateTime.now()

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
        val actorsAndDirectors = cast.filter { it.role == "ACTOR" || it.role == "DIRECTOR" }
        val records = actorsAndDirectors.map { CastMemberMapper.mapToRecord(it, db.newRecord(CAST_MEMBER)) }

        for (record in records) {
            record.fetchedOn = timestamp

            record.store()
        }

        return records
    }

    private fun splitIntoActorAndDirectorIds(cast: List<CastMemberRecord>): Pair<List<Long>, List<Long>> {
        val actors = cast.filter { it.role == "ACTOR" }
        val actorIds = actors.map { it.id!! }

        val directors = cast.filter { it.role == "DIRECTOR" }
        val directorIds = directors.map { it.id!! }

        return Pair(actorIds, directorIds)
    }

    override fun all(): List<Movie> {
        val lastCacheTimestamp = lastCacheTimestamp()

        val movies = db.selectFrom(MOVIE).where(MOVIE.FETCHED_ON.eq(lastCacheTimestamp)).fetch()

        return movies.map { MovieMapper.mapToDomain(it) }
    }

    private fun lastCacheTimestamp(): LocalDateTime {
        return db.selectDistinct(LAST_MOVIE_CACHE_FETCH_TIMESTAMP.FETCHED_ON)
            .from(LAST_MOVIE_CACHE_FETCH_TIMESTAMP)
            .limit(1)
            .fetchOne()!!
            .into(LocalDateTime::class.java)
    }
}

private object MovieMapper {

    fun mapToDomain(source: MovieRecord): Movie {
        return Movie(
            id = MovieId.Persisted(source.id!!),
            title = source.title!!,
            description = source.description!!,
            released = source.released!!,
            actorIds = source.actorRefs!!.mapNotNull { CastMemberId.Persisted(it!!) }.toList(),
            directorIds = source.directorRefs!!.mapNotNull { CastMemberId.Persisted(it!!) }.toList(),
            genreReferences = source.genreRefs!!.mapNotNull { it }.toSet(),
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
            this.actorRefs = actorIds.toTypedArray()
            this.directorRefs = directorIds.toTypedArray()
            this.genreRefs = source.genreReferences.toTypedArray()
            this.reference = source.reference
            this.providerShorthand = source.providerShorthand
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
            id = source.reference
            name = source.name
            role = source.role
            reference = source.reference
        }
    }
}
