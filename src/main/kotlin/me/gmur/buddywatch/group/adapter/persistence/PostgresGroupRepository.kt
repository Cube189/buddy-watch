package me.gmur.buddywatch.group.adapter.persistence

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.GroupId
import me.gmur.buddywatch.group.domain.model.GroupUrl
import me.gmur.buddywatch.group.domain.model.Provider
import me.gmur.buddywatch.group.domain.port.GroupRepository
import me.gmur.buddywatch.jooq.tables.Group.Companion.GROUP
import me.gmur.buddywatch.jooq.tables.Provider.Companion.PROVIDER
import me.gmur.buddywatch.jooq.tables.records.GroupRecord
import me.gmur.buddywatch.jooq.tables.records.ProviderRecord
import me.gmur.buddywatch.jooq.tables.references.TOKEN
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

    override fun ofMember(token: Token): Group {
        val groupId = db.select(TOKEN.GROUP_ID).from(TOKEN)
            .where(TOKEN.ID.eq(token.id.value))
            .fetch()
            .first()
            .into(Long::class.java)

        val group = db.fetchOne(GROUP, GROUP.ID.eq(groupId)) ?: throw GroupNotFound(groupId, token)

        val providers = db.selectFrom(PROVIDER)
            .where(PROVIDER.GROUP_ID.eq(group.id))
            .fetch()

        return mapper.mapToDomain(group, providers)
    }

    override fun exists(group: Group): Boolean {
        return db.fetchExists(
            db.selectFrom(GROUP).where(GROUP.ID.eq(group.id.value))
        )
    }

    override fun exists(url: GroupUrl): Boolean {
        return db.fetchExists(
            db.selectFrom(GROUP).where(GROUP.URL.eq(url.toString()))
        )
    }
}

private object GroupMapper {

    fun mapToRecord(source: Group, base: GroupRecord): GroupRecord {
        val mapped = base.apply {
            name = source.name
            memberCount = source.memberCount
            votesPerMember = source.votesPerMember
            url = source.url.toString()
        }

        if (mapped.id == null) {
            mapped.changed(GROUP.ID, false)
        }

        return mapped
    }

    fun mapToRecord(source: Group, groupId: Long?, base: ProviderRecord): Collection<ProviderRecord> {
        val providers = source.providers

        val mapped = providers.map {
            base.also { b ->
                b.id = it.id
                b.name = it.name
                b.shorthand = it.shorthand
                b.groupId = groupId
            }
        }

        for (record in mapped) {
            if (record.id == null) {
                record.changed(PROVIDER.ID, false)
            }
        }

        return mapped
    }

    fun mapToDomain(group: GroupRecord, providers: Collection<ProviderRecord>): Group {
        val mappedProviders = providers.map { mapToDomain(it) }.toSet()
        val url = GroupUrl(group.url!!)

        return Group(
            group.name!!,
            group.memberCount!!,
            group.votesPerMember!!,
            mappedProviders,
            url,
            GroupId.Persisted(group.id!!)
        )
    }

    private fun mapToDomain(source: ProviderRecord): Provider {
        return Provider(source.shorthand!!, source.name!!, source.id)
    }
}
