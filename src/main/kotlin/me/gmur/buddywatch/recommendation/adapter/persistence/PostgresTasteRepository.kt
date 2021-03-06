package me.gmur.buddywatch.recommendation.adapter.persistence

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.auth.domain.model.TokenId
import me.gmur.buddywatch.jooq.tables.records.ActorsTasteRecord
import me.gmur.buddywatch.jooq.tables.records.DecadesTasteRecord
import me.gmur.buddywatch.jooq.tables.records.DirectorsTasteRecord
import me.gmur.buddywatch.jooq.tables.records.GenresTasteRecord
import me.gmur.buddywatch.jooq.tables.references.ACTORS_TASTE
import me.gmur.buddywatch.jooq.tables.references.DECADES_TASTE
import me.gmur.buddywatch.jooq.tables.references.DIRECTORS_TASTE
import me.gmur.buddywatch.jooq.tables.references.GENRES_TASTE
import me.gmur.buddywatch.recommendation.domain.model.taste.Actor
import me.gmur.buddywatch.recommendation.domain.model.taste.ActorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Decade
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.DirectorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Genre
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Taste
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.jooq.DSLContext
import org.jooq.Result
import org.springframework.stereotype.Repository

@Repository
class PostgresTasteRepository(private val db: DSLContext) : TasteRepository {

    override fun store(actors: ActorsTaste, token: Token) {
        val records = ActorsTasteMapper.mapToRecords(actors, token, db)

        for (record in records) record.store()
    }

    override fun store(decades: DecadesTaste, token: Token) {
        val record = DecadesTasteMapper.mapToRecord(decades, token, db)

        record.store()
    }

    override fun store(directors: DirectorsTaste, token: Token) {
        val records = DirectorsTasteMapper.mapToRecord(directors, token, db)

        for (record in records) record.store()
    }

    override fun store(genres: GenresTaste, token: Token) {
        val records = GenresTasteMapper.mapToRecords(genres, token, db)

        for (record in records) record.store()
    }

    override fun getActors(token: Token): ActorsTaste {
        val actors = db.selectFrom(ACTORS_TASTE)
            .where(ACTORS_TASTE.TOKEN_ID.eq(token.id.value))
            .fetch()

        return ActorsTasteMapper.mapToDomain(actors)
    }

    override fun getDecades(token: Token): DecadesTaste {
        val decades = db.selectFrom(DECADES_TASTE)
            .where(DECADES_TASTE.TOKEN_ID.eq(token.id.value))
            .fetch()
            .first()

        return DecadesTasteMapper.mapToDomain(decades)
    }

    override fun getDirectors(token: Token): DirectorsTaste {
        val directors = db.selectFrom(DIRECTORS_TASTE)
            .where(DIRECTORS_TASTE.TOKEN_ID.eq(token.id.value))
            .fetch()

        return DirectorsTasteMapper.mapToDomain(directors)
    }

    override fun getGenres(token: Token): GenresTaste {
        val genres = db.selectFrom(GENRES_TASTE)
            .where(GENRES_TASTE.TOKEN_ID.eq(token.id.value))
            .fetch()

        return GenresTasteMapper.mapToDomain(genres)
    }

    override fun of(token: Token): Taste {
        return Taste(
            token,
            decades = getDecades(token),
            genres = getGenres(token),
            actors = getActors(token),
            directors = getDirectors(token)
        )
    }
}

private object DecadesTasteMapper {

    fun mapToDomain(source: DecadesTasteRecord): DecadesTaste {
        return DecadesTaste(source.decades!!.map { Decade(it!!) }.toSet())
    }

    fun mapToRecord(
        source: DecadesTaste,
        token: Token,
        db: DSLContext
    ): DecadesTasteRecord {
        return db.newRecord(DECADES_TASTE).apply {
            decades = source.decades.map { it.value }.toTypedArray()
            tokenId = if (token.id == TokenId.New) null else token.id.value
        }
    }
}

private object DirectorsTasteMapper {

    fun mapToDomain(source: Result<DirectorsTasteRecord>): DirectorsTaste {
        val tastes = source.map { Director(it.name!!, it.reference!!) }

        return DirectorsTaste(tastes.toSet())
    }

    fun mapToRecord(
        source: DirectorsTaste,
        token: Token,
        db: DSLContext
    ): List<DirectorsTasteRecord> {
        return source.directors.map {
            db.newRecord(DIRECTORS_TASTE).apply {
                name = it.name
                reference = it.reference
                tokenId = token.id.value
            }
        }
    }
}

private object GenresTasteMapper {

    fun mapToDomain(source: Result<GenresTasteRecord>): GenresTaste {
        val tastes = source.map { Genre(it.value!!, it.shorthand!!, it.reference!!) }

        return GenresTaste(tastes.toSet())
    }

    fun mapToRecords(
        source: GenresTaste,
        token: Token,
        db: DSLContext
    ): List<GenresTasteRecord> {
        return source.genres.map {
            db.newRecord(GENRES_TASTE).apply {
                value = it.name
                shorthand = it.shorthand
                reference = it.reference
                tokenId = token.id.value
            }
        }
    }
}

private object ActorsTasteMapper {

    fun mapToDomain(source: Result<ActorsTasteRecord>): ActorsTaste {
        val tastes = source.map { Actor(it.name!!, it.reference!!) }

        return ActorsTaste(tastes.toSet())
    }

    fun mapToRecords(
        source: ActorsTaste,
        token: Token,
        db: DSLContext
    ): List<ActorsTasteRecord> {
        return source.actors.map {
            db.newRecord(ACTORS_TASTE).apply {
                name = it.name
                reference = it.reference
                tokenId = token.id.value
            }
        }
    }
}
