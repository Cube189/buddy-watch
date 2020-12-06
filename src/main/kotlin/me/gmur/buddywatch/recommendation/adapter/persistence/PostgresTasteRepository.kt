package me.gmur.buddywatch.recommendation.adapter.persistence

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.auth.domain.model.TokenId
import me.gmur.buddywatch.jooq.tables.records.DecadesTasteRecord
import me.gmur.buddywatch.jooq.tables.references.DECADES_TASTE
import me.gmur.buddywatch.recommendation.domain.model.taste.ActorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.Decade
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.DecadesTasteId
import me.gmur.buddywatch.recommendation.domain.model.taste.DirectorsTaste
import me.gmur.buddywatch.recommendation.domain.model.taste.GenresTaste
import me.gmur.buddywatch.recommendation.domain.port.TasteRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class PostgresTasteRepository(private val db: DSLContext) : TasteRepository {

    override fun store(actors: ActorsTaste, token: Token): ActorsTaste {
        TODO("Not yet implemented")
    }

    override fun store(decades: DecadesTaste, token: Token): DecadesTaste {
        val record = DecadesTasteMapper.mapToRecord(decades, token, db.newRecord(DECADES_TASTE))

        record.store()

        return DecadesTasteMapper.mapToDomain(record)
    }

    override fun store(directors: DirectorsTaste, token: Token): DirectorsTaste {
        TODO("Not yet implemented")
    }

    override fun store(genres: GenresTaste, token: Token): GenresTaste {
        TODO("Not yet implemented")
    }

    override fun getActors(token: Token): ActorsTaste {
        TODO("Not yet implemented")
    }

    override fun getDecades(token: Token): DecadesTaste {
        TODO("Not yet implemented")
    }

    override fun getDirectors(token: Token): DirectorsTaste {
        TODO("Not yet implemented")
    }

    override fun getGenres(token: Token): GenresTaste {
        TODO("Not yet implemented")
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
        val d = source.values.map { it.value }

        val mapped = base.apply {
            id = if (source.id == DecadesTasteId.New) null else source.id.value
            decades = d.toTypedArray()
            tokenId = if (token.id == TokenId.New) null else token.id.value
        }

        if (source.id == DecadesTasteId.New) {
            mapped.changed(DECADES_TASTE.ID, false)
        }

        return mapped
    }
}
