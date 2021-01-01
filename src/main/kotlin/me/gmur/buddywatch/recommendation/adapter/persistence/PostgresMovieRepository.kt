package me.gmur.buddywatch.recommendation.adapter.persistence

import me.gmur.buddywatch.jooq.tables.records.CastMemberRecord
import me.gmur.buddywatch.jooq.tables.records.MovieRecord
import me.gmur.buddywatch.jooq.tables.references.CAST_MEMBER
import me.gmur.buddywatch.jooq.tables.references.MOVIE
import me.gmur.buddywatch.recommendation.domain.model.CastMember
import me.gmur.buddywatch.recommendation.domain.model.CastMemberId
import me.gmur.buddywatch.recommendation.domain.model.Movie
import me.gmur.buddywatch.recommendation.domain.model.MovieId
import me.gmur.buddywatch.recommendation.domain.port.MovieRepository
import org.jooq.DSLContext
import org.jooq.Result
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class PostgresMovieRepository(private val db: DSLContext) : MovieRepository {

    override fun store(movies: List<Movie>) {
        val castIds = movies.map { store(it.cast) }
        val moviesWithCastIds = movies zip castIds
        val records = moviesWithCastIds
            .map { pair -> pair.first to pair.second.map { it.id!! } }
            .map { MovieMapper.mapToRecord(it.first, it.second, db.newRecord(MOVIE)) }

        records.forEach { it.fetchedOn = LocalDateTime.now() }
        db.batchStore(records).execute()
    }

    private fun store(cast: Set<CastMember>): Set<CastMemberRecord> {
        val records = cast.map { CastMemberMapper.mapToRecord(it, db.newRecord(CAST_MEMBER)) }

        records.forEach { it.fetchedOn = LocalDateTime.now() }
        records.forEach { it.store() }

        return records.toSet()
    }
}

private object MovieMapper {

    fun mapToDomain(movie: MovieRecord, cast: Result<CastMemberRecord>): Movie {
        return Movie(
            id = MovieId.Persisted(movie.id!!),
            title = movie.title!!,
            description = movie.description!!,
            released = movie.released!!,
            cast = cast.map { CastMemberMapper.mapToDomain(it) }.toSet(),
            genreIds = movie.genreIds!!.mapNotNull { it }.toSet(),
            reference = movie.reference!!,
        )
    }

    fun mapToRecord(source: Movie, castIds: List<Long>, base: MovieRecord): MovieRecord {
        return base.apply {
            this.title = source.title
            this.description = source.description
            this.released = source.released
            this.castIds = castIds.toTypedArray()
            this.genreIds = source.genreIds.toTypedArray()
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
