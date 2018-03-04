package com.mustafa.arif.fbapp.backend.model


class Paging {

    private var previous: String? = null

    private var next: String? = null

    fun getPrevious(): String? {
        return previous
    }

    fun setPrevious(previous: String) {
        this.previous = previous
    }

    fun getNext(): String? {
        return next
    }

    fun setNext(next: String) {
        this.next = next
    }

    override fun toString(): String {
        return "ClassPojo [previous = $previous, next = $next]"
    }
}