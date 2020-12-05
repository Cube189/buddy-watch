package me.gmur.buddywatch.group.adapter.persistence

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.GroupUrl
import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.jooq.tables.JGroup.GROUP
import me.gmur.buddywatch.jooq.tables.JProvider.PROVIDER
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import me.gmur.buddywatch.group.adapter.persistence.GroupMapper as mapper

@Repository
class PostgresGroupRepository(private val db: DSLContext) : GroupRepository {

    override fun store(group: Group): Group {
        val groupRecord = mapper.mapToRecord(group, db.newRecord(GROUP))
        groupRecord.store()

        val providerRecords = mapper.mapToRecord(group, groupRecord.id, db.newRecord(PROVIDER))
        providerRecords.forEach { it.store() }

        return mapper.mapToDomain(groupRecord, providerRecords)
    }

    override fun get(groupUrl: GroupUrl): Group {
        val group = db.fetchOne(GROUP, GROUP.URL.eq(groupUrl.toString()))
        val providers = db.selectFrom(PROVIDER)
            .where(PROVIDER.GROUP_ID.eq(group!!.id))
            .fetch()

        return mapper.mapToDomain(group, providers)
    }

    override fun exists(group: Group): Boolean {
        return db.fetchExists(
            db.selectFrom(GROUP).where(GROUP.ID.eq(group.id))
        )
    }

    override fun exists(url: GroupUrl): Boolean {
        return db.fetchExists(
            db.selectFrom(GROUP).where(GROUP.URL.eq(url.toString()))
        )
    }
}
