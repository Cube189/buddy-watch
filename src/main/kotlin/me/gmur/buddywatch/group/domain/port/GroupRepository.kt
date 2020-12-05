package me.gmur.buddywatch.group.domain.port

import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.GroupUrl

interface GroupRepository {

    fun store(group: Group): Group

    fun get(groupUrl: GroupUrl): Group

    fun exists(group: Group): Boolean

    fun exists(url: GroupUrl): Boolean
}
