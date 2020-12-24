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
import me.gmur.buddywatch.recommendation.domain.model.taste.ActorsTasteId
import me.gmur.buddywatch.recommendation.domain.model.taste.Decade
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTasteId
import me.gmur.buddywatch.recommendation.domain.model.taste.Director
import me.gmur.buddywatch.recommendation.domain.model.taste.DirectorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.DirectorsTasteId
import me.gmur.buddywatch.recommendation.domain.model.taste.Genre
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTasteId
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class PostgresTasteRepository(private val db: DSLContext) : TasteRepository {

    override fun store(actors: ActorsTaste, token: Token): ActorsTaste {
        val record = ActorsTasteMapper.mapToRecord(actors, token, db.newRecord(ACTORS_TASTE))

        record.store()

        return ActorsTasteMapper.mapToDomain(record)
    }

    override fun store(decades: DecadesTaste, token: Token): DecadesTaste {
        val record = DecadesTasteMapper.mapToRecord(decades, token, db.newRecord(DECADES_TASTE))

        record.store()

        return DecadesTasteMapper.mapToDomain(record)
    }

    override fun store(directors: DirectorsTaste, token: Token): DirectorsTaste {
        val record = DirectorsTasteMapper.mapToRecord(directors, token, db.newRecord(DIRECTORS_TASTE))

        record.store()

        return DirectorsTasteMapper.mapToDomain(record)
    }

    override fun store(genres: GenresTaste, token: Token): GenresTaste {
        val record = GenresTasteMapper.mapToRecord(genres, token, db.newRecord(GENRES_TASTE))

        record.store()

        return GenresTasteMapper.mapToDomain(record)
    }

    override fun getActors(token: Token): ActorsTaste {
        val actors = db.selectFrom(ACTORS_TASTE)
            .where(ACTORS_TASTE.TOKEN_ID.eq(token.id.value))
            .fetch()
            .first()

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
            .first()

        return DirectorsTasteMapper.mapToDomain(directors)
    }

    override fun getGenres(token: Token): GenresTaste {
        val genres = db.selectFrom(GENRES_TASTE)
            .where(GENRES_TASTE.TOKEN_ID.eq(token.id.value))
            .fetch()
            .first()

        return GenresTasteMapper.mapToDomain(genres)
    }
}

private object DecadesTasteMapper {

    fun mapToDomain(source: DecadesTasteRecord): DecadesTaste {
        return DecadesTaste(
            source.decades!!.map { Decade(it!!) }.toSet(),
            DecadesTasteId.Persisted(source.id!!)
        )
    }

    fun mapToRecord(
        source: DecadesTaste,
        token: Token,
        base: DecadesTasteRecord
    ): DecadesTasteRecord {
        val mapped = base.apply {
            id = if (source.id == DecadesTasteId.New) null else source.id.value
            decades = source.values.map { it.value }.toTypedArray()
            tokenId = if (token.id == TokenId.New) null else token.id.value
        }

        if (source.id == DecadesTasteId.New) {
            mapped.changed(DECADES_TASTE.ID, false)
        }

        return mapped
    }
}

private object DirectorsTasteMapper {

    fun mapToDomain(source: DirectorsTasteRecord): DirectorsTaste {
        return DirectorsTaste(
            source.names!!.map { Director(it!!) }.toSet(),
            DirectorsTasteId.Persisted(source.id!!)
        )
    }

    fun mapToRecord(
        source: DirectorsTaste,
        token: Token,
        base: DirectorsTasteRecord
    ): DirectorsTasteRecord {
        val mapped = base.apply {
            id = if (source.id == DirectorsTasteId.New) null else source.id.value
            names = source.values.map { it.name }.toTypedArray()
            tokenId = if (token.id == TokenId.New) null else token.id.value
        }

        if (source.id == DirectorsTasteId.New) {
            mapped.changed(DIRECTORS_TASTE.ID, false)
        }

        return mapped
    }
}

private object GenresTasteMapper {

    fun mapToDomain(source: GenresTasteRecord): GenresTaste {
        return GenresTaste(
            source.values!!.map { Genre(it!!) }.toSet(),
            GenresTasteId.Persisted(source.id!!)
        )
    }

    fun mapToRecord(
        source: GenresTaste,
        token: Token,
        base: GenresTasteRecord
    ): GenresTasteRecord {
        val mapped = base.apply {
            id = if (source.id == GenresTasteId.New) null else source.id.value
            values = source.values.map { it.value }.toTypedArray()
            tokenId = if (token.id == TokenId.New) null else token.id.value
        }

        if (source.id == GenresTasteId.New) {
            mapped.changed(GENRES_TASTE.ID, false)
        }

        return mapped
    }
}

private object ActorsTasteMapper {

    fun mapToDomain(source: ActorsTasteRecord): ActorsTaste {
        return ActorsTaste(
            source.names!!.map { Actor(it!!) }.toSet(),
            ActorsTasteId.Persisted(source.id!!)
        )
    }

    fun mapToRecord(
        source: ActorsTaste,
        token: Token,
        base: ActorsTasteRecord
    ): ActorsTasteRecord {
        val mapped = base.apply {
            id = if (source.id == ActorsTasteId.New) null else source.id.value
            names = source.names.map { it.name }.toTypedArray()
            tokenId = if (token.id == TokenId.New) null else token.id.value
        }

        if (source.id == ActorsTasteId.New) {
            mapped.changed(ACTORS_TASTE.ID, false)
        }

        return mapped
    }
}
