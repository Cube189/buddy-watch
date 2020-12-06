package me.gmur.buddywatch.common.domain.model

abstract class Entity<T : Id<*>> {

    abstract val id: T

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (
            javaClass != other?.javaClass ||
            id != (other as Entity<*>).id
        ) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString() = "${this.javaClass.simpleName}($id)"
}
