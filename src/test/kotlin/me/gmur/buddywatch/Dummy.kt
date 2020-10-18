package me.gmur.buddywatch

// A simple class-under-testing
internal class Dummy {

    fun foo(): Int {
        return 0
    }

    fun bar(): String {
        throw IllegalStateException()
    }
}
