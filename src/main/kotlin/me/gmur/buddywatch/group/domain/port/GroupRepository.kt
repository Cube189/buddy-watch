package me.gmur.buddywatch.group.domain.port

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.Group

interface GroupRepository {

    fun store(group: Group): Group

    fun exists(group: Group): Boolean
}
