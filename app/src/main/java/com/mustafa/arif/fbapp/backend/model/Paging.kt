package com.mustafa.arif.fbapp.backend.model

import android.os.Parcel
import android.os.Parcelable


class Paging() : Parcelable {

    private var previous: String? = null

    private var next: String? = null

    constructor(parcel: Parcel) : this() {
        previous = parcel.readString()
        next = parcel.readString()
    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(previous)
        parcel.writeString(next)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Paging> {
        override fun createFromParcel(parcel: Parcel): Paging {
            return Paging(parcel)
        }

        override fun newArray(size: Int): Array<Paging?> {
            return arrayOfNulls(size)
        }
    }
}